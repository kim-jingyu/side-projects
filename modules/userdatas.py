from pymongo import MongoClient
client = MongoClient('localhost', 27017)
db = client.kraftto

krafton_paticipants = [
    "01.강철구",
    "02.김병철",
    "03.none",
    "04.김진태",
    "05.박건률",
    "06.none",
    "07.배성재",
    "08.소경현",
    "09.손용재",
    "10.none",
    "11.안진석",
    "12.양혜림",
    "13.이동건",
    "14.이서영",
    "15.이준용",
    "16.장세영",
    "17.none",
    "18.정준희",
    "19.조유진",
    "20.조윤성",
    "21.조현지",
    "22.진상하",
    "23.최선빈",
    "24.none",
    "25.홍선우",
    "26.황상필",
    "27.황창조",
    "28.김건우",
    "29.김상우",
    "30.none",
    "31.김진규",
    "32.김형민",
    "33.none",
    "34.손정원",
    "35.송수민",
    "36.none",
    "37.조윤희",
    "38.none",
    "39.김민규",
    "40.김병현",
    "41.김의훈",
    "42.민상기",
    "43.이서연",
    "44.none",
    "45.이세욱",
    "46.이지원",
    "47.이찬우",
    "48.정진환",
    "49.조정민",
    "50.천지영",
    "51.현재훈",
]


def insert_paticipants_db():
    for paticipant in krafton_paticipants:
        paticipants = {
            'username': paticipant,
        }
        print(paticipant)

        db.paticipants.insert_one(paticipants)


insert_paticipants_db()
