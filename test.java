@RunWith(SpringJUnit4ClassRunner.class)
@ContectConfiguration(copy here)
public class CompareResultServiceTests {

	@InjectBean
	private CompareResultService service;
	
	@Mock
	private ComparatorDao compDao;
	
	@Autowired
	private ComparatorStatsPojo comparatorStatsPojo; 
	
	@Test
	public void getComparisonTest(){
		
		GetSkuInfoResponseBody parsedCdapResponse = mock(GetSkuInfoResponseBody.class);
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> cdapResponse = mock(CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>.class);
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> cbResponse = mock(CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>.class);
		
		Mockito.when(cdapResponse.getResponseObject().getBody()).thenReturn(parsedCdapResponse);
		Mockito.when(cbResponse.getResponseObject().getBody()).thenReturn(parsedCdapResponse);
		
		GetSkuInfoRequest cdapSkuInfoRequest = cdapResponse.getRequestObject();
		GetSkuInfoRequest cbSkuInfoRequest = cbResponse.getRequestObject();
		
		ComparatorPojo comp = new ComparatorPojo();
		comp.setCbRequest(cbSkuInfoRequest);
		comp.setCdapRequest(cdapSkuInfoRequest);
		comp.setCbResponse(cbResponse);
		comp.setCdapResponse(cdapResponse);
		
		List<SKUInfDetail> cbSkuInfDetail = comp.getCbResponse().getSkuInfDetail();
		List<SKUInfDetail> cdapSkuInfDetail = comp.setCdapResponse().getSkuInfDetail();
		Mockito.when(cbSkuInfDetail.size()).thenReturn(1);
		Mockito.when(cdapSkuInfDetail.size()).thenReturn(2);
		Mockito.when(cdapSkuInfDetail.containsAll()).thenReturn(true);
		
		boolean equalLists = false;
		comp.setSource("PRICE_COMPARATOR");
		comp.setSuccessInd(equalLists);
		Mockito.when(compDao.saveComparatorValues(comp)).thenReturn("");
		
		SKUCompareStatsPojo skPojo = new SKUCompareStatsPojo();
		
		comparatorStatsPojo.getSkuCompareStatsPojos().add(skPojo);
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> inpCbResp = new CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>();
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> inpCdapResp = new CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>();
		service.getComparison(inpCdapResp,inpCbResp);
		
		Assert.assertNotNull(comparatorStatsPojo);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = Exception.class)
	public void getComparisonTestException(){
		
		GetSkuInfoResponseBody parsedCdapResponse = mock(GetSkuInfoResponseBody.class);
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> cdapResponse = mock(CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>.class);
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> cbResponse = mock(CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>.class);
		
		Mockito.when(cdapResponse.getResponseObject().getBody()).thenReturn(parsedCdapResponse);
		Mockito.when(cbResponse.getResponseObject().getBody()).thenReturn(parsedCdapResponse);
		
		GetSkuInfoRequest cdapSkuInfoRequest = cdapResponse.getRequestObject();
		GetSkuInfoRequest cbSkuInfoRequest = cbResponse.getRequestObject();
		
		ComparatorPojo comp = new ComparatorPojo();
		comp.setCbRequest(cbSkuInfoRequest);
		comp.setCdapRequest(cdapSkuInfoRequest);
		comp.setCbResponse(cbResponse);
		comp.setCdapResponse(cdapResponse);
		
		List<SKUInfDetail> cbSkuInfDetail = comp.getCbResponse().getSkuInfDetail();
		List<SKUInfDetail> cdapSkuInfDetail = comp.setCdapResponse().getSkuInfDetail();
		Mockito.when(cbSkuInfDetail.size()).thenReturn(1);
		Mockito.when(cdapSkuInfDetail.size()).thenReturn(2);
		Mockito.when(cdapSkuInfDetail.containsAll()).thenThrow(Exception.class);
		
		boolean equalLists = false;
		comp.setSource("PRICE_COMPARATOR");
		comp.setSuccessInd(equalLists);
		Mockito.when(compDao.saveComparatorValues(comp)).thenReturn("");
		
		SKUCompareStatsPojo skPojo = new SKUCompareStatsPojo();
		
		comparatorStatsPojo.getSkuCompareStatsPojos().add(skPojo);
		
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> inpCbResp = new CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>();
		CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse> inpCdapResp = new CoreServiceResponse<GetSkuInfoRequest, GetSkuInfoResponse>();
		service.getComparison(inpCdapResp,inpCbResp);
		
		Assert.assertNotNull(comparatorStatsPojo);
		
	}

}