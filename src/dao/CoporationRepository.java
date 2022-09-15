package dao;

import java.util.ArrayList;
import dto.Coporation;

public class CoporationRepository {
	private ArrayList<Coporation> listOfCop = new ArrayList<Coporation>();
	private static CoporationRepository instance = new CoporationRepository();
	
	public static CoporationRepository getInstance() {
		return instance;
	}
	
	public CoporationRepository() {
		Coporation ahnLab=new Coporation("214-81","AhnLab");
		ahnLab.setRepresentative("강석균");
		ahnLab.setOfficeAddress("경기 성남시 분당구 판교역로 220");
		ahnLab.setFormOfBusiness("중견기업");
		ahnLab.setIndustry("시스템 소프트웨어 개발 및 공급업");
		ahnLab.setFoundationDate(19950318);
		ahnLab.setCertification("CISA,CISSP,정보보안기사");
		ahnLab.setFileName("AhnLab.png");
		ahnLab.setSales("156,550,808원");
		ahnLab.setLocation("ahnLabLoc.png");
		ahnLab.setAvgIncome("7000천만원");
		ahnLab.setComparison("noData.png");
		ahnLab.setEmployee("1187명");
		
		Coporation cisco=new Coporation("ForeignCorporation","CiscoSystems");
		cisco.setRepresentative("Chuck Robbins");
		cisco.setOfficeAddress("San Jose, California");
		cisco.setFormOfBusiness("major company");
		cisco.setIndustry("manufactures and sells networking hardware, software, telecommunications equipment and other high-technology services and products");
		cisco.setFoundationDate(19841210);
		cisco.setCertification("CCNA,CCNP,CCIE");
		cisco.setFileName("Cisco.png");
		cisco.setSales("51.9 billion USD");
		cisco.setLocation("noData.png");
		cisco.setAvgIncome("1억3219만원");
		cisco.setComparison("noData.png");
		cisco.setEmployee("71,883명");
		
		Coporation kakao=new Coporation("120-81","KaKao");
		kakao.setRepresentative("여민수/조수용");
		kakao.setOfficeAddress("제주 제주시 첨단로 242");
		kakao.setFormOfBusiness("대기업");
		kakao.setIndustry("포털 및 기타 인터넷 정보매개 서비스업");
		kakao.setFoundationDate(19950216);
		kakao.setCertification("없음");
		kakao.setFileName("KaKao.png");
		kakao.setSales("1,173,924,652원");
		kakao.setLocation("kakaoLoc.png");
		kakao.setAvgIncome("1억");
		kakao.setComparison("kakaoCom.png");
		kakao.setEmployee("2701명");
		
		listOfCop.add(ahnLab);
		listOfCop.add(cisco);
		listOfCop.add(kakao);
		
		
	}
	public ArrayList<Coporation> getAllCops(){
		return listOfCop;
	}
	
	public Coporation getCopById(String copId) {
		
		Coporation copById=null;
		
		for(int i=0;i<listOfCop.size();i++) {
			Coporation cop=listOfCop.get(i);
			if(cop != null && cop.getCopId() != null && cop.getCopId().equals(copId)) {
				copById=cop;
				break;
			}
		}
		
		return copById;
	}
	public void addCorporation(Coporation corporation) {
		listOfCop.add(corporation);
	}
}
