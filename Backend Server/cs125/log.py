import logging
import config


for lib in config.log_levels.keys():
    logger = logging.getLogger(lib)
    logger.setLevel(config.log_levels[lib])
    print("Setting %s log level to %s" % (lib, config.log_levels[lib]))

def get_logger(name):
    logger = logging.getLogger(name)
    logger.setLevel(logging.DEBUG)
    fh = logging.FileHandler('cs125.log')
    fh.setLevel(logging.DEBUG)
    # create console handler with a higher log level
    ch = logging.StreamHandler()
    ch.setLevel(logging.DEBUG)
    logger.addHandler(ch)
    logger.addHandler(fh)
    # create formatter and add it to the handlers
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    return logger
