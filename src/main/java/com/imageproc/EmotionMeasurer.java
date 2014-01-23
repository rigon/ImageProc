package com.imageproc;

public class EmotionMeasurer {
	
	//Thresholds
	//Hapiness
	private double TH1, TH2, TH3, TH4;
	private double TH_d1, TH_d2, TH_d3, TH_d4; //?
	//Sadness 
	private double TS1, TS2, TS3, TS4;
	private double TS_d1, TS_d2, TS_d3, TS_d4; //?
	//Neutrality
	//private double TN1, TN2, TN3, TN4;
	private double TN_d1, TN_d2, TN_d3, TN_d4; //?
	//Surpeise
	private double TSp1, TSp2, TSp3, TSp4;
	private double TSp_d1, TSp_d2, TSp_d3, TSp_d4; //?
	//Hunger
	//private double TA1, TA2, TA3, TA4;
	private double TA_d1, TA_d2, TA_d3, TA_d4; //?
	
	//Partes da imagem recolhida
	MatImage upper_eyelid, lower_eyelid, upper_lip, lower_lip;
	
	//Construtor
	public EmotionMeasurer(MatImage upper_eyelid, MatImage lower_eyelid, MatImage upper_lip, MatImage lower_lip){
		this.upper_eyelid=upper_eyelid;
		this.lower_eyelid=lower_eyelid;
		this.lower_lip=lower_lip;
		this.upper_lip=upper_lip;
	}
	
	//Avalia a emoção
	public void measureEmotion(){
		
		//Emoção verificada
		//boolean Happy, Sad, Neutral, Surprized, Angry;
		
		//Condições de 'Happy'
		boolean happy1, happy2, happy3, happy4, happy5;
		//Condições de 'Sad'
		boolean sad1, sad2, sad3, sad4, sad5;
		//Condições de 'Neutral'
		boolean neutral1, neutral2 = false, neutral3, neutral4, neutral5;
		//Condições de 'Surprized'
		boolean surprized1, surprized2, surprized3, surprized4;
		//Condições de 'Angry'
		boolean angry1, angry2, angry3, angry4;
		
		//Avalia se está 'Happy'
		happy1=happyCondition1(this.TH1, this.TH2);
		happy2=happyCondition2(this.TH3, this.TH4);
		happy3=happyCondition3(this.TH1, this.TH2, this.TH3, this.TH4);
		happy4=happyCondition4(this.TH_d1, this.TH_d2);
		happy5=happyCondition5(this.TH_d3, this.TH_d4);

		//Avalia se está 'Sad'
		sad1=sadCondition1(this.TS1, this.TS2);
		sad2=sadCondition2(this.TS3, this.TS4);
		sad3=sadCondition3(this.TS1, this.TS2, this.TS3, this.TS4);
		sad4=sadCondition4(this.TS_d1, this.TS_d2);
		sad5=sadCondition5(this.TS_d3, this.TS_d4);

		//Avalia se está 'Neutral'
		neutral1=neutralCondition1();
		neutral2=neutralCondition2();
		neutral3=neutralCondition3(this.TN_d1, this.TN_d2);
		neutral4=neutralCondition4(this.TN_d3, this.TN_d4);
		neutral5=neutralCondition5(this.TN_d1, this.TN_d2, this.TN_d3, this.TN_d4);

		//Avalia se está 'Surprized'
		surprized1=surprizedCondition1(this.TSp1, this.TSp2);
		surprized2=surprizedCondition2(this.TSp3, this.TSp4);
		surprized3=surprizedCondition3(this.TSp_d1, this.TSp_d2);
		surprized4=surprizedCondition4(this.TSp_d3, this.TSp_d4);
		
		//Avalia se está 'Angry'
		angry1=angryCondition1();
		angry2=angryCondition2();
		angry3=angryCondition3(this.TA_d1, this.TA_d2);
		angry4=angryCondition4(this.TA_d3, this.TA_d4);

		//Verifica se existe alguma das 5 emoções
		if(happy1 && happy2 && happy3 && happy4 && happy5)
			System.out.println("Happy (Not Serious)");
		//Happy=true;
		if(sad1 && sad2 && sad3 && sad4 && sad5)
			System.out.println("Sad (Not Serious)");
		//Sad=true;
		if(neutral1 && neutral2 && neutral3 && neutral4 && neutral5)
			System.out.println("Neutral (Serious)");
		//Neutral=true;
		if(surprized1 && surprized2 && surprized3 && surprized4)
			System.out.println("Surprized (Not Serious)");
		//Surprized=true;
		if(angry1 && angry2 && angry3 && angry4)
			System.out.println("Angry (Not Serious)");
		//Angry=true;
	}
	
	//Avalia a 1ª condição da emoção 'Happy'
	public boolean happyCondition1(double Th1, double Th2){
		
		double gradientOfLowerLip=calculateGradientLowerLip(this.lower_lip);
		
		if(Th1 < gradientOfLowerLip && gradientOfLowerLip < Th2 && Th1<0 && Th2<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 2ª condição da emoção 'Happy'
	public boolean happyCondition2(double Th3, double Th4){
		
		double gradientOfUpperLip=calculateGradientUpperLip(this.upper_lip);
		
		if(Th3 < gradientOfUpperLip && gradientOfUpperLip < Th4 && Th3<0 && Th4<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 3ª condição da emoção 'Happy'
	public boolean happyCondition3(double Th1, double Th2, double Th3, double Th4){
		
		if(Th1 <= Th3 && Th2 <= Th4)
			return true;
		else
			return false;
	}

	//Avalia a 4ª condição da emoção 'Happy'
	public boolean happyCondition4(double Th_d1, double Th_d2){
		
		double gradientOfLowerEyelid=calculateGradientLowerEyelid(this.lower_eyelid);
		
		if(Th_d1 < gradientOfLowerEyelid && gradientOfLowerEyelid < Th_d2 && Th_d1<0 && Th_d2<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 5ª condição da emoção 'Happy'
	public boolean happyCondition5(double Th_d3, double Th_d4){
		
		double gradientOfUpperEyelid=calculateGradientUpperEyelid(this.upper_eyelid);
		
		if(Th_d3 < gradientOfUpperEyelid && gradientOfUpperEyelid < Th_d4 && Th_d3>0 && Th_d4>0)
			return true;
		else
			return false;
	}

	//Avalia a 1ª condição da emoção 'Sad'
	public boolean sadCondition1(double Ts1, double Ts2){
		
		double gradientOfLowerLip=calculateGradientLowerLip(this.lower_lip);
		
		if(Ts1 < gradientOfLowerLip && gradientOfLowerLip < Ts2 && Ts1>0 && Ts2>0)
			return true;
		else
			return false;
	}
	
	//Avalia a 2ª condição da emoção 'Sad'
	public boolean sadCondition2(double Ts3, double Ts4){
		
		double gradientOfUpperLip=calculateGradientUpperLip(this.upper_lip);
		
		if(Ts3 < gradientOfUpperLip && gradientOfUpperLip < Ts4 && Ts3>0 && Ts4>0)
			return true;
		else
			return false;
	}

	//Avalia a 3ª condição da emoção 'Sad'
	public boolean sadCondition3(double Ts1, double Ts2, double Ts3, double Ts4){
		
		if(Ts1 <= Ts3 && Ts2 <= Ts4)
			return true;
		else
			return false;
	}

	//Avalia a 4ª condição da emoção 'Sad'
	public boolean sadCondition4(double Ts_d1, double Ts_d2){
		
		double gradientOfLowerEyelid=calculateGradientLowerEyelid(this.lower_eyelid);
		
		if(Ts_d1 < gradientOfLowerEyelid && gradientOfLowerEyelid < Ts_d2 && Ts_d1<0 && Ts_d2<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 5ª condição da emoção 'Sad'
	public boolean sadCondition5(double Ts_d3, double Ts_d4){
		
		double gradientOfUpperEyelid=calculateGradientUpperEyelid(this.upper_eyelid);
		
		if(Ts_d3 < gradientOfUpperEyelid && gradientOfUpperEyelid < Ts_d4 && Ts_d3>0 && Ts_d4>0)
			return true;
		else
			return false;
	}
	
	//Avalia a 1ª condição da emoção 'Neutral'
	public boolean neutralCondition1(){
		if(calculateGradientLowerLip(this.lower_lip) < 1E-7)
			return true;
		else
			return false;
	}
	
	//Avalia a 2ª condição da emoção 'Neutral'
	public boolean neutralCondition2(){
		if(calculateGradientUpperLip(this.upper_lip) < 1E-7)
			return true;
		else
			return false;
	}

	//Avalia a 3ª condição da emoção 'Neutral'
	public boolean neutralCondition3(double Tn_d1, double Tn_d2){
		
		double gradientOfLowerEyelid=calculateGradientLowerEyelid(this.lower_eyelid);
		
		if(Tn_d1 < gradientOfLowerEyelid && gradientOfLowerEyelid < Tn_d2 && Tn_d1<0 && Tn_d2<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 4ª condição da emoção 'Neutral'
	public boolean neutralCondition4(double Tn_d3, double Tn_d4){
		
		double gradientOfUpperEyelid=calculateGradientUpperEyelid(this.upper_eyelid);
		
		if(Tn_d3 < gradientOfUpperEyelid && gradientOfUpperEyelid < Tn_d4 && Tn_d3>0 && Tn_d4>0)
			return true;
		else
			return false;
	}
	
	//Avalia a 5ª condição da emoção 'Neutral'
	public boolean neutralCondition5(double Tn1, double Tn2, double Tn3, double Tn4){
		
		if(Tn1<1E-7 || Tn2<1E-7 || Tn3<1E-7 || Tn4<1E-7)
			return true;
		else
			return false;
	}
	
	//Avalia a 1ª condição da emoção 'Surprized'
	public boolean surprizedCondition1(double Tsp1, double Tsp2){
		
		double gradientOfLowerLip=calculateGradientLowerLip(this.lower_lip);
		
		if(Tsp1 < gradientOfLowerLip && gradientOfLowerLip < Tsp2 && Tsp1>0 && Tsp2>0)
			return true;
		else
			return false;
	}

	//Avalia a 2ª condição da emoção 'Surprized'
	public boolean surprizedCondition2(double Tsp3, double Tsp4){
		
		double gradientUpperLip=calculateGradientUpperLip(this.upper_lip);
		
		if(Tsp3 < gradientUpperLip && gradientUpperLip < Tsp4 && Tsp3<0 && Tsp4<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 3ª condição da emoção 'Surprized'
	public boolean surprizedCondition3(double Tsp_d1, double Tsp_d2){
		
		double gradientLowerEyelid=calculateGradientLowerEyelid(this.lower_eyelid);
		
		if(Tsp_d1 < gradientLowerEyelid && gradientLowerEyelid < Tsp_d2 && Tsp_d1<0 && Tsp_d2<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 4ª condição da emoção 'Surprized'
	public boolean surprizedCondition4(double Tsp_d3, double Tsp_d4){
		
		double gradientUpperEyelid=calculateGradientUpperEyelid(this.upper_eyelid);
		
		if(Tsp_d3 < gradientUpperEyelid && gradientUpperEyelid < Tsp_d4 && Tsp_d3>0 && Tsp_d4>0)
			return true;
		else
			return false;
	}

	//Avalia a 1ª condição da emoção 'Angry'
	public boolean angryCondition1(){
		if(calculateGradientLowerLip(this.lower_lip) < 1E-7)
			return true;
		else
			return false;
	}

	//Avalia a 2ª condição da emoção 'Angry'
	public boolean angryCondition2(){
		if(calculateGradientUpperLip(this.upper_lip) < 1E-7)
			return true;
		else
			return false;
	}

	//Avalia a 3ª condição da emoção 'Angry'
	public boolean angryCondition3(double Ta_d1, double Ta_d2){
		
		double gradientOfLowerEyelid=calculateGradientLowerEyelid(this.lower_eyelid);
		
		if(Ta_d1 < gradientOfLowerEyelid && gradientOfLowerEyelid < Ta_d2 && Ta_d1<0 && Ta_d2<0)
			return true;
		else
			return false;
	}
	
	//Avalia a 4ª condição da emoção 'Angry'
	public boolean angryCondition4(double Ta_d3, double Ta_d4){
		
		double gradientOfUpperEyelid=calculateGradientUpperEyelid(this.upper_eyelid);
		
		if(Ta_d3 < gradientOfUpperEyelid && gradientOfUpperEyelid < Ta_d4 && Ta_d3>0 && Ta_d4>0)
			return true;
		else
			return false;
	}

	//Calcula o gradiente do lábio inferior
	public static double calculateGradientLowerLip(MatImage lower_lip){
		
		float gradientLowerLip=0.0f;
		
		//Some necessary code here
		
		return gradientLowerLip;
	}
	
	//Calcula o gradiente do lábio superior
	public static double calculateGradientUpperLip(MatImage upper_lip){
		
		float gradientUpperLip=0.0f;
		
		//Some necessary code here
		
		return gradientUpperLip;
	}
	
	//Calcula o gradiente da pálpebra inferior
	public static double calculateGradientLowerEyelid(MatImage lower_eyelid){
		
		float gradientLowerEyelid=0.0f;
		
		//Some necessary code here
		
		return gradientLowerEyelid;
	}

	//Calcula o gradiente da pálpebra superior
	public static double calculateGradientUpperEyelid(MatImage upper_eyelid){
		
		float gradientUpperEyelid=0.0f;
		
		//Some necessary code here
		
		return gradientUpperEyelid;
	}
	
	
}
