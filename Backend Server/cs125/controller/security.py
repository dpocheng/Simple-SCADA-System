from sanic.response import json
import log
from .exception import exceptions
import base64
import config

logger = log.get_logger(__name__)
AUTH_KEY = "Authorization"
IGNORE_METHODS = ["OPTIONS"]

class Security():
    ''' Handles incoming requests '''
    def __init__(self, app):
        @app.middleware('request')
        async def auth_middleware(request):
            if (request.method in IGNORE_METHODS):
                ''' Support preflight '''
                return

            auth = request.headers.get(AUTH_KEY, None)
            if (auth is None):
                raise exceptions.Forbidden()

            _, key = auth.split()
            if (key is None or key == ""):
                raise exceptions.Forbidden()
