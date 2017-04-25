import sys
import time
import config
import asyncio
from db import es_util
from controller import server

try:
    import uvloop as async_loop
except ImportError:
    async_loop = asyncio

def __handle_args():
    if (len(sys.argv)) < 2:
        return
    for i in range(1, len(sys.argv), 2):
        key = sys.argv[i][2:]
        setattr(config, key, (type(getattr(config, key)))(sys.argv[i+1]))

processes = []
def __launch_process(func, args):
    global processes

    workers = config.workers
    for _ in range(workers):
        process = Process(target=func, args=args)
        process.daemon = True #TODO custom id?
        process.start()
        processes.append(process)

    for process in processes:
        process.join()

    #TODO stop all processes (Above calls should block)
    #TODO https://github.com/channelcat/sanic/blob/master/sanic/sanic.py
    #TODO signals and stopping


def __kill_processes():
    global processes

    for process in processes:
        process.terminate()

if __name__ == '__main__':
    __handle_args()
    es_util.setup()
    loop = async_loop.new_event_loop()
    asyncio.set_event_loop(loop)

    #TODO run nest in a different process with x process counts
    print("Run run run")
    server.start(loop)
