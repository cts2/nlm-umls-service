package edu.mayo.cts2.ushik.mu1;

import flexjson.JSONSerializer;
import gov.ahrq.ushik.webservices.AdministeredItem;
import gov.ahrq.ushik.webservices.Measure;
import gov.ahrq.ushik.webservices.QualityDataElement;
import gov.ahrq.ushik.webservices.SimpleItemList;
import gov.ahrq.ushik.webservices.SimpleListItem;
import gov.ahrq.ushik.webservices.USHIK;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UshikCacheUtil {

    private gov.ahrq.ushik.webservices.USHIK service;
    private String authKey;

    /**
     * Default Constructor
     */
    public UshikCacheUtil() {
        init();
    }

    /**
     * Initialization method:  creates Web Service Connection and Authentication Key
     */
    private void init() {
        this.setService(new gov.ahrq.ushik.webservices.USHIKService().getUSHIKPort());
        this.setAuthKey(this.getService().authenticateSession("", ""));
    }

    /**
     * Returns an Administered Item which is identified by a specific item key
     * Item keys might be originally determined by a search using the searchByItemName(String) method
     * @param itemKey
     * @return 
     */
    public AdministeredItem getAdministeredItem(int itemKey) {
        AdministeredItem instance = this.getService().getAdministeredItem(this.getAuthKey(), itemKey);
        return instance;
    }

    /**
     * Returns a list of AdministeredItem references whose name matches the itemName parameter
     * @param itemName
     * @return 
     */
    public List<SimpleListItem> searchByItemName(String itemName) {
        SimpleItemList results = service.searchByItemName(this.getAuthKey(), itemName);
        List<SimpleListItem> items = results.getListItems();
        return items;
    }

    protected USHIK getService() {
        return service;
    }

    protected void setService(USHIK service) {
        this.service = service;
    }

    protected String getAuthKey() {
        return authKey;
    }

    protected void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
    private List< QDE> getQDEs(){
   	 SimpleItemList measuresList = service.getAllMeaningfulUseMeasures(authKey);
      HashMap<String, String> nqfIds = new HashMap<String, String>();
      List<QDE> qdes = new ArrayList<QDE>();
      int qbeCounter = 0;
      if (measuresList != null) {
          for (SimpleListItem _sli : measuresList.getListItems()) {
              if (!nqfIds.containsValue(_sli.getItemName())) {
                  Measure _m = service.getMeasure(authKey, _sli.getItemKey());
                  if (_m != null && _m.getMeasureNumber().startsWith("NQF")) {
                      System.out.println(_m.getMeasureNumber() + ": " + _m.getItemName());
                      nqfIds.put(_m.getMeasureNumber(), _m.getItemName());
                      qbeCounter += _m.getQualityDataElements().size();
                      for(QualityDataElement qd: _m.getQualityDataElements()){
                    	  qdes.add(createQDE(qd));
                      }
                  }
//            	  if(qdes.size() > 10){
//            		  break;
//            	  }
              }
          }
      }

      System.out.println("Total QBE's: " + qbeCounter);
    	return qdes;
    }
    
    public QDE createQDE(QualityDataElement qde){
    	QDE newQde = new QDE();
    	newQde.name = isEmpty(qde.getItemName())? "Not Provided":qde.getItemName();
    	newQde.type = isEmpty(qde.getCommonItemType().getName())? "Not Provided":qde.getCommonItemType().getName();
    	newQde.definition = isEmpty(qde.getDefinitionText())? "Not Provided":qde.getDefinitionText();
    	newQde.qDSId = isEmpty(qde.getQDSId())? "Not Provided":qde.getQDSId();
    	newQde.qDSDataType = isEmpty(qde.getQDSDataType())? "Not Provided":qde.getQDSDataType();
    	newQde.dataTypeSpecificAttributes = isEmpty(qde.getQDSdatatypeSpecificAttributes())? "Not Provided":qde.getQDSdatatypeSpecificAttributes();
    	newQde.standardConcept = isEmpty(qde.getStandardConcept())? "Not Provided":qde.getStandardConcept();
    	newQde.context = isEmpty(qde.getContextName())? "Not Provided":qde.getContextName();
    	newQde.registrationAuthority = isEmpty(qde.getResponsibleOrganization().getOrgName())? "Not Provided":qde.getResponsibleOrganization().getOrgName();
    	newQde.status = isEmpty(qde.getAdministrativeStatus())? "Not Provided":qde.getAdministrativeStatus();
    	newQde.submissionDate = isEmpty(qde.getItemSubmission().getSubmissionDate().toString())? "Not Provided":qde.getItemSubmission().getSubmissionDate().toString();
    	newQde.responsibleOrg = isEmpty(qde.getResponsibleOrganization().getOrgName())? "Not Provided":qde.getResponsibleOrganization().getOrgName();
    	newQde.stewardOrg = isEmpty(qde.getOrganization().getOrgName())? "Not Provided":qde.getOrganization().getOrgName();
    	return newQde;
    }
    public void cacheUshikQDEsToJSON() throws IOException{
    	List<QDE> qdes = getQDEs();
    	 JSONSerializer serializer = new JSONSerializer().exclude("*.class", "description"); //.transform(new IterableTransformer(), "QDE");
    	 FileWriter f0 = new FileWriter("QDE.json");     	 
 //   	 String test = serializer.deepSerialize(qdes);
    	 serializer.serialize(qdes, f0);
    }
	public static void main(String[] args) {
		UshikCacheUtil util = new UshikCacheUtil();
		try {
			util.cacheUshikQDEsToJSON();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isEmpty(String string) {
	    return string == null || string.length() == 0;
	}

	class QDE{
        public String getName() {
			return name;
		}
		public String getType() {
			return type;
		}
		public String getDefinition() {
			return definition;
		}
		public String getqDSId() {
			return qDSId;
		}
		public String getqDSDataType() {
			return qDSDataType;
		}
		public String getDataTypeSpecificAttributes() {
			return dataTypeSpecificAttributes;
		}
		public String getStandardConcept() {
			return standardConcept;
		}
		public String getContext() {
			return context;
		}
		public String getRegistrationAuthority() {
			return registrationAuthority;
		}
		public String getStatus() {
			return status;
		}
		public String getResponsibleOrg() {
			return responsibleOrg;
		}
		public String getSubmissionDate() {
			return submissionDate;
		}
		public String getSubmittingOrg() {
			return submittingOrg;
		}
		public String getStewardOrg() {
			return stewardOrg;
		}
		String name;
        String type;
        String definition;
        String qDSId;
        String qDSDataType;
        String dataTypeSpecificAttributes;
        String standardConcept;
        String context;
        String registrationAuthority;
        String status;
        String responsibleOrg;
        String submissionDate;
        String submittingOrg;
        String stewardOrg;
	}

}
