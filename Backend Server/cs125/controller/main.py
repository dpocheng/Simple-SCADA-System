from sanic.response import json, text
from sanic import Blueprint
from sanic.views import HTTPMethodView
from sanic.exceptions import InvalidUsage, NotFound
import ujson

from .exception.exceptions import QuotaError
from db import es_util
import log
import config

logger = log.get_logger(__name__)

def str_to_bool(string):
    """
    Parses string into boolean
    """
    string = string.lower()
    return True if string == "true" or string == "yes" else False

class StoreView(HTTPMethodView):
    def post(self, request):
        if (request.body is None):
            raise InvalidUsage('Body cant be empty.')

        logger.debug(str(request.body))
        data = ujson.loads(str(request.body, 'utf-8'))
        es_util.index(data)
        return json({'message': "ok"})


class FetchView(HTTPMethodView):
    def get(self, request):
        logger.debug(request.args)
        result = []
        for kw in request.args:
            res = es_util.search(kw, request.args[kw][0])
            hits = res['hits']
            count = hits['total']
            results = hits['hits']
            for res in results:
                result.append(res['_source'])
        return text(str(result))

class FetchAllView(HTTPMethodView):
    def get(self, request):
        try:
            from_ = int(request.args['from'][0])
            size = int(request.args['size'][0])
        except (KeyError, ValueError) as e:
            raise InvalidUsage('Provide ints from and size as queries.')
        result = []
        res = es_util.get(from_, size)
        hits = res['hits']
        count = hits['total']
        results = hits['hits']
        for res in results:
            result.append(res['_source'])
        return text(str(result))

bp = Blueprint('mail')
bp.add_route(StoreView(), '/tag')
bp.add_route(FetchView(), '/get')
bp.add_route(FetchAllView(), '/getall')
