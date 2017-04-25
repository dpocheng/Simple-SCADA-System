from sanic.response import json
from sanic.exceptions import SanicException, InvalidUsage, NotFound
from controller import main
from . import exceptions
import log
logger = log.get_logger(__name__)

@main.bp.exception(SanicException)
def sanic_exception(request, exception):
    return return_response(str(exception), exception.status_code)

@main.bp.exception(NotFound)
def sanic_exception_nf(request, exception):
    return return_response(str(exception), exception.status_code)

@main.bp.exception(InvalidUsage)
def sanic_exception_invalid(request, exception):
    return return_response(str(exception), exception.status_code)

@main.bp.exception(ValueError)
def value_error(request, exception):
    return return_response('Found unexpected value', 400)



class CustomException():
    def __init__(self, app):
        @app.exception(exceptions.Unauthorized)
        def unauthorized(request, exception):
            logger.warn("Blocked unauthorized request from %s", request.headers.get("Remote-Addr", "NO-IP"))
            return return_response(str(exception), 401)

        @app.exception(exceptions.Forbidden)
        def forbidden(request, exception):
            logger.warn("Blocked unauthorized request from %s", request.headers.get("Remote-Addr", "NO-IP"))
            return return_response(str(exception), 403)

        @app.exception(exceptions.QuotaError)
        def quota(request, exception):
            return return_response(str(exception), 400)


def return_response(message, status_code):
    response = json({"error": message}, status_code)
    return response
