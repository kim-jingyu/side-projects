$.ajax({
    type: "GET",
    url: "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99",
    data: {},
    success: function (response) {
        // console.log(response)
        let dobong_gu = response['RealtimeCityAir']['row'][11]
        let gu_name = dobong_gu['MSRSTE_NM']
        let mise_value = dobong_gu['IDEX_MVL']

        console.log(gu_name, mise_value)
    }
})

$.ajax({
    type: "GET",
    url: "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99",
    data: {},
    success: function (response) {
        console.log(response['RealtimeCityAir']['row'])
    }
})

$.ajax({
    type: "GET",
    url: "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99",
    data: {},
    success: function(response){
        let all_gu = response['RealtimeCityAir']['row']

        for (let i = 0; i < all_gu.length; i++) {
            let gu_name = all_gu[i]['MSRSTE_NM']
            let mise_value = all_gu[i]['IDEX_MVL']

            console.log(gu_name, mise_value)
        }
    }
})