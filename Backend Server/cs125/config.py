# Configuration file
import logging


host = "192.168.0.69"
port = 8080


elastic_search = {
    'host': '192.168.0.10',
    'port': 9200,
    'index': 'cs125',
    'use_aws': False
}

aws = {
    'access_key': '',
    'secret_key': '',
    'region': ''
}


enable_security = False

#======Controller============
workers = 1



#==========Versions============
versions = {
    'controller': 1.0,
}


#============logging===========
#Levels
log_levels = {
    'Sanic': logging.DEBUG,
    'sanic_cors': logging.DEBUG,
    'cassandra': logging.WARN,
    'RedisCache': logging.INFO,
}
