import logging
import time
import twobeatstg

console_handler = logging.StreamHandler()
_logger = logging.getLogger("STG")
_logger.addHandler(console_handler)
_logger.setLevel(logging.INFO)

def run():
    while True:
        _logger.info("You can now start working on the container")
        time.sleep(3)


if __name__ == '__main__':
    run()
