import logging
import time
from twobeatstg.output.messages.meter_reading import GenerateMeterReadingRequestMessage

console_handler = logging.StreamHandler()
_logger = logging.getLogger("STG")
_logger.addHandler(console_handler)
_logger.setLevel(logging.INFO)

def run():
    while True:
        _logger.info("You can now start working on the container")
        time.sleep(3)


if __name__ == '__main__':
    params = {
        "message_id": "ECASA111111",
        "request_id": "11111111111111111111111",
        "serial_number": "UAAEEDN10100002247",
        "cups_name": "CUPSECASA000001",
    }
    meter_reding = GenerateMeterReadingRequestMessage(**params)
    run()


