package es.tid.rsvp.objects.subobjects.subtlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkStateAttributeTLVTypes;

public class SubTransponderTLVFEC extends SubTLV {

	private int standardizedFormat;
	private int input; 
	private int FEC_id;
	
	
	public SubTransponderTLVFEC(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_FEC);
	}
	
	public SubTransponderTLVFEC(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	
	@Override
	public void encode() {

int offset =4;
		
		tlv_bytes[offset]=(byte)((standardizedFormat<<7)|(input<<6)|(FEC_id>>>8));
		
		offset = offset + 1;
		
		//tlv_bytes[offset]=(byte)(modulationID&0xFF);
		
			  
	}
	
	protected void decode(){
		
		int offset = 4;
		
		log.info("******************* Decodificando SubTransponderTLVFEC *****************");
		
		//Comprobar y revisar 
		standardizedFormat=(tlv_bytes[offset]&0xE0)>>>7;
		input=(tlv_bytes[offset]&0x1E)>>>6;
		FEC_id=((tlv_bytes[offset]&0x01)<<8)|(tlv_bytes[offset+1]&0xFF);
				
		
		log.info("Standardized Format : " + standardizedFormat + ".");
		log.info("Input : " + input + ".");
		log.info("FEC ID : " + FEC_id + ".");
				
		log.info("***************** FIN Decodificando SubTransponderTLVFEC ***************");
	}
	
	public String toString(){
		String str =  "<SubTransponderTLVFEC" + " Standardized Format: " + standardizedFormat + "\n Input: " + input + "\n FEC ID: " + FEC_id; 
		str+=">";
		return str;
	}

	public int getStandardizedFormat() {
		return standardizedFormat;
	}

	public void setStandardizedFormat(int standardizedFormat) {
		this.standardizedFormat = standardizedFormat;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public int getFEC_id() {
		return FEC_id;
	}

	public void setFEC_id(int fEC_id) {
		FEC_id = fEC_id;
	}
	
	
	
	
}
