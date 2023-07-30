import re

# . (ca.e) -> 하나의 문자를 의미함
# ^ (^de) -> 문자열의 시작
# $ (se$) -> 문자열의 끝
p = re.compile("ca.e")


def print_match(param):
    if param:
        print(param.group())
    else:
        print("매칭되지 않았습니다.")


# match : 주어진 문자열의 처음부터 일치하는지 확인
m = p.match("caffe")
print_match(m)

# search : 주어진 문자열의 처음부터 일치하는지 확인
