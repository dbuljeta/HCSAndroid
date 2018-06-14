import sys
import requests
import json
from datetime import datetime
import time
import threading

times =  []

class Requester(threading.Thread):
    def __init__(self, url, credentials):
        threading.Thread.__init__(self)
        self.url = url
        self.credentials = credentials
    def run(self):
        response = requests.post(self.url, self.credentials)
        parsed_json = json.loads(response.text)

        numberOfPills = len(parsed_json["pills"])
        print '---------------------------------------------'
        for i in range(0,numberOfPills):
            print 'Name: ' + parsed_json["pills"][i]["name"]
            print 'Description: ' + parsed_json["pills"][i]["description"]
            numberOfIntakes = parsed_json["pills"][i]["numberOfIntakes"]
            print 'Number of intakes: ' + str(parsed_json["pills"][i]["numberOfIntakes"])
            for j in range(0, numberOfIntakes):
                print 'Time Of intake ' + str(j + 1) + ': ' + parsed_json["pills"][i]["intakes"][j]["timeOfIntake"]
                vrime = datetime.time(datetime.strptime(parsed_json["pills"][i]["intakes"][j]["timeOfIntake"], '%H:%M:%S'))
                times.append(vrime)
                print vrime
                if len(parsed_json["pills"][i]["intakes"][j]["event_intakes"]) > 0:
                    print 'Is taken: ' +  str(parsed_json["pills"][i]["intakes"][j]["event_intakes"][0]["taken"])
                else:
                    print "Is taken: False"
            print '---------------------------------------------'

if __name__ == '__main__':
    API_ENDPOINT = "http://hcs.herokuapp.com/api/login"
    credentials = {
        "name": "dbuljeta",
        "password": "123456"
    }
    counter = 0
    while counter < 1440:
        request = Requester(API_ENDPOINT, credentials)
        request.start()
        counter += 1
        cTime = time.ctime(time.time())
        print(cTime)

        cTime = str(cTime[11:17] + '00')
        cTime = datetime.time(datetime.strptime(cTime, '%H:%M:%S'))
        print cTime

        for k in range(0,len(times)):    
            print(times[k])
            if (times[k] == cTime):
                print "Uspjeli smo!"
        time.sleep(60)


# API_ENDPOINT = "http://hcs.herokuapp.com/api/login"
# data = {
#     "name": "dbuljeta",
#     "password": "123456"
# }
# response = requests.post(url = API_ENDPOINT, data = data)
# parsed_json = json.loads(response.text)

# numberOfPills = len(parsed_json["pills"])

# print '---------------------------------------------'
# for i in range(0,numberOfPills):
#     print 'Name: ' + parsed_json["pills"][i]["name"]
#     print 'Description: ' + parsed_json["pills"][i]["description"]
#     numberOfIntakes = parsed_json["pills"][i]["numberOfIntakes"]
#     print 'Number of intakes: ' + str(parsed_json["pills"][i]["numberOfIntakes"])
#     for j in range(0, numberOfIntakes):
#         print 'Time Of intake ' + str(j + 1) + ': ' + parsed_json["pills"][i]["intakes"][j]["timeOfIntake"]
#         vrime = datetime.time(datetime.strptime(parsed_json["pills"][i]["intakes"][j]["timeOfIntake"], '%H:%M:%S'))
#         times.append(vrime)
#         print vrime
#         if len(parsed_json["pills"][i]["intakes"][j]["event_intakes"]) > 0:
#             print 'Is taken: ' +  str(parsed_json["pills"][i]["intakes"][j]["event_intakes"][0]["taken"])
#         else:
#             print "Is taken: False"
#     print '---------------------------------------------'
    
# cTime = time.ctime(time.time())
# print(cTime)

# cTime = str(cTime[11:19])
# cTime = datetime.time(datetime.strptime(cTime, '%H:%M:%S'))
# print cTime

# for k in range(0,len(times)):    
#     print(times[k])


