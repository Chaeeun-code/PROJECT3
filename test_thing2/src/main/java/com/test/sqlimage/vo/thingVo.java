package com.test.sqlimage.vo;

public class thingVo {

	private int price;
	private String thing_id;
	private String company_id;
	private String name;
	private int num;
	private String explain;
	private String image_path; 
	private int click_count;
	private int woman;
	private int man;
	private int teenager;
	private int middleaged;
	private int elseage;
	
	
	public thingVo() {
	}

	public thingVo( int price,String image_path, String thing_id, String explain, int num, String company_id, 
			String name, int click_count, int woman, int man, int teenager, int middleaged, int elseage) {
		super();
		
	
		this.price = price;
		this.image_path = image_path;
		this.thing_id = thing_id;
		this.explain = explain;
		this.num = num;
		this.company_id = company_id;
		this.name = name;
		this.click_count = click_count;
		this.woman = woman;
		this.man = man;
		this.teenager = teenager;
		this.middleaged = middleaged;
		this.elseage = elseage;
		
		
	}



	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	  


	    // Getter & Setter
	    public String getthing_id() {
	        return thing_id;
	    }

	    public void setthing_id(String thing_id) {
	        this.thing_id = thing_id;
	    }

	    public String getcompany_id() {
	        return company_id;
	    }

	    public void setcompany_id(String company_id) {
	        this.company_id = company_id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getNum() {
	        return num;
	    }

	    public void setNum(int num) {
	        this.num = num;
	    }

	    public String getExplain() {
	        return explain;
	    }

	    public void setExplain(String explain) {
	        this.explain = explain;
	    }


		public String getImage_path() {
			return image_path;
		}

		public void setImage_path(String image_path) {
			this.image_path = image_path;
		}

		public Object getInfo() {
			// TODO Auto-generated method stub
			return null;
		}
	 
}
