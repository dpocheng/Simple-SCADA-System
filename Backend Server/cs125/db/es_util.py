import config
import uuid
from elasticsearch import Elasticsearch, RequestsHttpConnection
from requests_aws4auth import AWS4Auth

es = None
index_name = config.elastic_search['index']


def getClient():
    global es
    # if es is None:
        # raise Exception("Elasticsearch was not initialized.")
    # else:
        # return es
    return es

def index(data):
    es.index(index=index_name, doc_type='tag', id=str(uuid.uuid1()), body=data)

def search(kw, val):
    return es.search(
        index=index_name,
        body={
            "sort" : [
                { "time" : {"order" : "desc"}},
            ],
            "query": {
                "match": {
                    kw: val
                }
            }
        })

def get(from_=0, size=50):
    return es.search(
        index=index_name,
        body={
            "from" : from_, "size" : size,
            # "query" : {
            #     "term" : { "user" : "kimchy" }
            # }
        })


def setup():
    global es
    awsauth = AWS4Auth(config.aws['access_key'], config.aws['secret_key'], config.aws['region'], 'es')
    es = Elasticsearch(
        hosts=[{'host': config.elastic_search['host'], 'port': config.elastic_search['port']}],
        http_auth=awsauth if config.elastic_search['use_aws'] else None,
        use_ssl=config.elastic_search['use_aws'],
        verify_certs=config.elastic_search['use_aws'],
        connection_class=RequestsHttpConnection
    )
    es.indices.refresh(index=index_name)
