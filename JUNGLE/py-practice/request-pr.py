import requests

request = requests.get('http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99')
request_json = request.json()

all_gus = request_json['RealtimeCityAir']['row']

for gu in all_gus:
    if gu['IDEX_MVL'] < 60:
        print('미세먼지 농도 60미만인 구 이름 : ', gu['MSRSTE_NM'])