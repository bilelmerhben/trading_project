package de.tekup.trading.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import lombok.Data;

@Data
@Service
public class CandleStickService {
	String value ="";
 static int c =0;
	private BinanceApiRestClient client;
	double bullishEngulfingRapport;
	double bullishHaramiRapport;
	double bullishDojiRapport;
	double bullishMoningStarRapport1;
	double bullishMoningStarRapport2;
	double bearishHaramiRapport;
	double bearishHangingManRapport;
	double bearishEngulfingRapport;
	double bearishEveningStarRapport1;
	double bearishEveningStarRapport2;
	double bearishGravestoneDojiRapport;
	//to day
	double lowPriceOfToday;
	double highPriceOfToday;
	double closePriceOfToday ;//Ct close
	double openPriceOfToDay; //Ct open
	double bodyBottomOfToday;
	double bodyTopOfToday;
	double shadowTopOfToDay;
	double shadowBottomOfToDay;

	// before one day
	double  closePriceBeforeOneDay ;//Ct-1 close
	double openPriceBeforeOneDay; //Ct-1 open
	double lowPriceBeforeOneDay;//ct-1 low
	double highPriceBeforeOneDay;//ct-1 high
	double bodyBottomBeforeOneDay;
	double bodyTopBeforeOneDay;
	
	// before two days
	double  closePriceBeforeTwoDay ;//Ct-2 close
	double openPriceBeforeTwoDay; // Ct-2 open
	double lowPriceBeforeTwoDay; //ct-2 low
	double highPriceBeforeTwoDay;// ct-2 heigh
	//after one day
	double closePriceAfterOneDay ;
	double openPriceAfterOneDay; 
	//after two day
	double closePriceAfterTwoDay ;
	double openPriceAfterTwoDay; 
	// stop-limit Order value
	double stopLimitOrder;
	double limitOrderToBuy ;
	double limitOrderToSell ;
	//bullish Engulfing
	 int nbrTotalOfBullishEngulfingPattern;
	 int nbrOfBullishEngulfingWithGoodResult;
	 int nbrOfBullishEngulfingWithBadResult;
	 //bullish Harami
	 int nbrTotalOfBullishHaramiPattern;
	 int nbrOfBullishHaramiWithGoodResult;
	 int nbrOfBullishHaramiWithBadResult;
	//bullish Doji
	 int nbrTotalOfBullishDojiPattern;
	 int nbrOfBullishDojiWithGoodResult;
	 int nbrOfBullishDojiWithBadResult;
	 //bullish Hammer
	 int nbrTotalOfBullishHammerPattern;
	 int nbrOfBullishHammerWithGoodResult;
	 int nbrOfBullishHammerWithBadResult;
	 //bullish MoningStar
	 int nbrTotalOfBullishMoningStarPattern;
	 int nbrOfBullishMoningStarWithGoodResult;
	 int nbrOfBullishMoningStarWithBadResult;
	 //bearishHarami
	 int nbrTotalOfBearishHaramiPattern;
	 int nbrOfBearishHaramiWithGoodResult;
	 int nbrOfBearishHaramiWithBadResult;
	 //bearishHangingMan
	 int nbrTotalOfBearishHangingManPattern;
	 int nbrOfBearishHangingManWithGoodResult;
	 int nbrOfBearishHangingManWithBadResult;
	 //bearishEngulfing
	 int nbrTotalOfBearishEngulfingPattern;
	 int nbrOfBearishEngulfingWithGoodResult;
	 int nbrOfBearishEngulfingWithBadResult;
	 //bearishEveningStar
	 int nbrTotalOfBearishEveningStarPattern;
	 int nbrOfBearishEveningStarWithGoodResult;
	 int nbrOfBearishEveningStarWithBadResult;
	 //bearishGravestoneDoji
	 int nbrTotalOfBearishGravestoneDojiPattern;
	 int nbrOfBearishGravestoneDojiWithGoodResult;
	 int nbrOfBearishGravestoneDojiWithBadResult;
    List<Candlestick> candlesticks; 
    HashMap<String,Integer> res = new HashMap<String, Integer>();
    HashMap<String,Integer> nvMap = new HashMap<String, Integer>();
    HashMap<String,Integer> lastMap = new HashMap<String, Integer>();
    
 
   
   
   
    public CandleStickService(BinanceApiRestClient bc)
    
    {
    	this.client=bc; 
    	
    
    }
  
    
	
   
  //----------------------------------------------------Bullish patterns--------------------------------------------------------------
  
//Bullish Engulfing Pattern 
    public void bullishEngulfing(int i) {
    	
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
        
        this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
    	// before one day
        this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
    	//after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen()); 
    	
        bullishEngulfingRapport = Math.abs(openPriceOfToDay -closePriceOfToday) /Math.abs(lowPriceOfToday -highPriceOfToday);
        
        if(  openPriceBeforeOneDay>closePriceBeforeOneDay &&
        	 openPriceOfToDay<closePriceOfToday	          &&
        	 closePriceBeforeOneDay> openPriceOfToDay     &&
        	 openPriceBeforeOneDay<closePriceOfToday      &&
        	 bullishEngulfingRapport > 0.6) 
        {
        	nbrTotalOfBullishEngulfingPattern++;
        	LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        	System.out.println("bullish Engulfing pattern at :"+ldt);
        	if(openPriceAfterOneDay<closePriceAfterOneDay) {
        		if(closePriceAfterTwoDay>= openPriceAfterOneDay || openPriceAfterTwoDay<closePriceAfterTwoDay) {
        			nbrOfBullishEngulfingWithGoodResult++;	
        		}
        		else {
        			nbrOfBullishEngulfingWithBadResult++;
        		}
        		}
        	else if (closePriceAfterOneDay>= openPriceOfToDay)
        	{
        		if(openPriceAfterTwoDay<closePriceAfterTwoDay)
        		{
        			nbrOfBullishEngulfingWithGoodResult++;
        		}
        		else
        			{
        			nbrOfBullishEngulfingWithBadResult++;
        			}
        	}
        	else 
        		{
        		nbrOfBullishEngulfingWithBadResult++;
        		}
        }
  }
    //Bullish Harami 
    public void bullishHarami(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
        
        this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
    	// before one day
        this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
      //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen()); 
        /*-------------------------*/
    	bullishHaramiRapport = Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(lowPriceBeforeOneDay -highPriceBeforeOneDay);
     
        if( openPriceBeforeOneDay>closePriceBeforeOneDay &&
        		openPriceOfToDay<closePriceOfToday	   &&
        	  closePriceBeforeOneDay< openPriceOfToDay  &&
        	  openPriceBeforeOneDay>closePriceOfToday   &&
        	  bullishHaramiRapport > 0.6) {
        	nbrTotalOfBullishHaramiPattern++;
	  LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        	
        	System.out.println("bullish Harami pattern at :"+ldt);
        	//testttt-------------------------
        	if(openPriceAfterOneDay<closePriceAfterOneDay) {
        		if(closePriceAfterTwoDay>= openPriceAfterOneDay || openPriceAfterTwoDay<closePriceAfterTwoDay) {
        			nbrOfBullishHaramiWithGoodResult++;
        			//System.out.println("good level 1");
        		}
        		else {
        			nbrOfBullishHaramiWithBadResult++;
        			//System.out.println("bad");
        		}
        		
        		}
        	
        	else if (closePriceAfterOneDay>= openPriceOfToDay)
        	{
        		if(openPriceAfterTwoDay<closePriceAfterTwoDay)
        		{

        			nbrOfBullishHaramiWithGoodResult++;
        			//System.out.println("good level 2");
        		}
        		else
        			{
        			nbrOfBullishHaramiWithBadResult++;
        			//System.out.println("bad");
        			}
        	}
        	else 
        		{
        		nbrOfBullishHaramiWithBadResult++;
        		//System.out.println("bad");
        		}
          
        	
        //-----------------------
        }
       
  }
    // Doji Pattern
 public void bullishDoji(int i) {
		//to day
     this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
     this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
     
     this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
     this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
 	// before one day
     this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
     this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
     
     this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
     this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
     //after one day
 	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
 	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
 	//after two days
 	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
 	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
     /*-------------------------*/
	 bullishDojiRapport = Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(lowPriceBeforeOneDay -highPriceBeforeOneDay);
	 if( openPriceBeforeOneDay>closePriceBeforeOneDay &&
			 lowPriceBeforeOneDay>lowPriceOfToday	   &&
			 highPriceOfToday - closePriceOfToday > 3* Math.abs(openPriceOfToDay - closePriceOfToday)  &&
			 (openPriceOfToDay - lowPriceOfToday)< ((highPriceOfToday-closePriceOfToday)/3)  &&
     	  bullishHaramiRapport > 0.6) {
		 nbrTotalOfBullishDojiPattern++;
		 LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
     	System.out.println(" bullish Doji pattern at :"+ldt);
     	
    	
     	//testttt-------------------------
    	if(openPriceAfterOneDay<closePriceAfterOneDay) {
    		if(closePriceAfterTwoDay>= openPriceAfterOneDay || openPriceAfterTwoDay<closePriceAfterTwoDay) {
    			nbrOfBullishDojiWithGoodResult++;
    			//System.out.println("good level 1");
    		}
    		else {
    			nbrOfBullishDojiWithBadResult++;
    			//System.out.println("bad");
    		}
    		
    		}
    	
    	else if (closePriceAfterOneDay>= openPriceOfToDay)
    	{
    		if(openPriceAfterTwoDay<closePriceAfterTwoDay)
    		{

    			nbrOfBullishDojiWithGoodResult++;
    			//System.out.println("good level 2");
    		}
    		else
    			{
    			nbrOfBullishDojiWithBadResult++;
    			//System.out.println("bad");
    			}
    	}
    	else 
    		{
    		nbrOfBullishDojiWithBadResult++;
    		//System.out.println("bad");
    		}
      
    	
    //-----------------------
     }
 }
 //Hammer Pattern
 public void bullishHammer(int i) {
		//to day
     this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
     this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
     
     this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
     this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
 	// before one day
     this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
     this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
     
     this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
     this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
     //after one day
 	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
 	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
 	//after two days
 	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
 	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
     /*-------------------------*/
	 if(openPriceOfToDay<closePriceOfToday) { //green candleStick
		 this.bodyBottomOfToday =openPriceOfToDay;
		 this.bodyTopOfToday = closePriceOfToday;
		 }
	 else {                                   //red candleStick
		 this.bodyBottomOfToday=closePriceOfToday;
		 this.bodyTopOfToday=openPriceOfToDay;
	 }
	 
	 if(openPriceBeforeOneDay >closePriceBeforeOneDay                                             &&
			 lowPriceBeforeOneDay >lowPriceOfToday                                                &&
			 (bodyBottomOfToday-lowPriceOfToday)> 2*Math.abs(openPriceOfToDay -closePriceOfToday) &&
			 (highPriceOfToday-bodyTopOfToday)<0.3*Math.abs(openPriceOfToDay -closePriceOfToday))
	 {
		 nbrTotalOfBullishHammerPattern++;
		
		 LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		 System.out.println(" bullish Hammer Pattern at :"+ldt);
		
	     	//testttt-------------------------
		 
	    	if(openPriceAfterOneDay<closePriceAfterOneDay) {
	    		if(closePriceAfterTwoDay>= openPriceAfterOneDay || openPriceAfterTwoDay<closePriceAfterTwoDay) {
	    			nbrOfBullishHammerWithGoodResult++;
	    		}
	    		else {
	    			nbrOfBullishHammerWithBadResult++;
	    		     }
	    		}
	    	else if (closePriceAfterOneDay>= openPriceOfToDay)
	    	       {
	    		     if(openPriceAfterTwoDay<closePriceAfterTwoDay)
	    		         {

	    			      nbrOfBullishHammerWithGoodResult++;
	    		         }
	    		     else
	    			     {
	    			      nbrOfBullishHammerWithBadResult++;
	    			     }
	    	        }
	    	else 
	    		   {
	    		   nbrOfBullishHammerWithBadResult++;
	    		   }
	      
	    	
	    //-----------------------
	 }
		 
	 
 }
// MorningStar Pattern
    public void bullishMoningStar(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+2).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+2).getHigh());
        this.openPriceOfToDay =Double.parseDouble(candlesticks.get(i+2).getOpen());
        this.closePriceOfToday =Double.parseDouble(candlesticks.get(i+2).getClose());
    	// before one day
        this.openPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getClose());
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getHigh());
        //before two day
        this.openPriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getClose());
        this.lowPriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getHigh());
        //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+4).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+4).getOpen());
        /*-------------------------*/
    	 bullishMoningStarRapport1 = Math.abs(openPriceBeforeTwoDay -closePriceBeforeTwoDay) /Math.abs(lowPriceBeforeTwoDay -highPriceBeforeTwoDay);
    	 bullishMoningStarRapport2 = Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(lowPriceBeforeOneDay -highPriceBeforeOneDay);
    	 if(openPriceBeforeTwoDay>closePriceBeforeTwoDay 																 &&
    			openPriceOfToDay<closePriceOfToday     																	 &&
    			bullishMoningStarRapport1> 0.6  		   	 															 &&
    			openPriceBeforeOneDay<closePriceBeforeTwoDay 															 &&
    			openPriceOfToDay>closePriceBeforeOneDay 																 &&
    			bullishMoningStarRapport2<0.3 																			 &&
    			(openPriceBeforeOneDay-closePriceBeforeOneDay)<Math.abs(openPriceBeforeTwoDay-closePriceBeforeTwoDay)    &&
    			(openPriceBeforeOneDay -closePriceBeforeOneDay)<Math.abs(openPriceOfToDay-closePriceOfToday)             &&
    			 lowPriceBeforeOneDay <lowPriceOfToday 																	 &&
    			 lowPriceBeforeOneDay <lowPriceBeforeTwoDay 															 &&
    			 highPriceBeforeOneDay<openPriceBeforeTwoDay 															 &&
    			 highPriceBeforeOneDay<closePriceOfToday) 
    	 {
    		 nbrTotalOfBullishMoningStarPattern++;
    		 LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+2).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
 		
    		 System.out.println("bullish MoningStar pattern at:"+ldt);
    		
    		//testttt-------------------------
 	    	if(openPriceAfterOneDay<closePriceAfterOneDay) {
 	    		if(closePriceAfterTwoDay>= openPriceAfterOneDay || openPriceAfterTwoDay<closePriceAfterTwoDay) {
 	    			nbrOfBullishMoningStarWithGoodResult++;
 	    		//	System.out.println("good level 1");
 	    		}
 	    		else {
 	    			nbrOfBullishMoningStarWithBadResult++;
 	    		//	System.out.println("bad");
 	    		}
 	    		
 	    		}
 	    	
 	    	else if (closePriceAfterOneDay>= openPriceOfToDay)
 	    	{
 	    		if(openPriceAfterTwoDay<closePriceAfterTwoDay)
 	    		{

 	    			nbrOfBullishMoningStarWithGoodResult++;
 	    			//System.out.println("good level 2");
 	    		}
 	    		else
 	    			{
 	    			nbrOfBullishMoningStarWithBadResult++;
 	    			//System.out.println("bad");
 	    			}
 	    	}
 	    	else 
 	    		{
 	    		nbrOfBullishMoningStarWithBadResult++;
 	    		//System.out.println("bad");
 	    		}
 	      
 	    	
 	    //-----------------------
    	 }
    	
    }
    
    //------------------------------------------------Bearish Patterns---------------------------------------------------------------
   //Bearish Harami Pattern
    public void bearishHarami(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
        
        this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
    	// before one day
        this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
        
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
        //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
        /*-------------------------*/
    	bearishHaramiRapport = Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(highPriceBeforeOneDay-lowPriceBeforeOneDay);
    if(openPriceBeforeOneDay<closePriceBeforeOneDay &&
    		openPriceOfToDay>closePriceOfToday 		&&
    		bearishHaramiRapport>0.6 				&&
    		closePriceOfToday>openPriceBeforeOneDay &&
    		openPriceOfToDay<closePriceBeforeOneDay) 
    {
    	nbrTotalOfBearishHaramiPattern++;
		 LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	
		 System.out.println("bearish Harami pattern at:"+ldt);
		 //TEST---------------------
		 if(openPriceAfterOneDay>closePriceAfterOneDay) {
	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay || closePriceAfterTwoDay<=openPriceAfterOneDay) {
	    			nbrOfBearishHaramiWithGoodResult++;
	    			System.out.println("good bearish result level 1");
	    		}
	    		else //closePriceAfterTwoDay<=openPriceAfterOneDay --openPriceAfterOneDay<=openPriceOfToDay
	    		{
	    			nbrOfBearishHaramiWithBadResult++;
	    			System.out.println("bad bearish result level 1");
	    		}
	    		
	    		}
	    	else if (openPriceAfterOneDay<=openPriceOfToDay)
	    	{
	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay)
 	    		{

	    			nbrOfBearishHaramiWithGoodResult++;
	    			System.out.println("good bearish result level 2");
 	    		}
 	    		else
 	    			{
 	    			nbrOfBearishHaramiWithBadResult++;
 	    			System.out.println("bad bearish result level 2");
 	    			
 	    			}
	    		
	    	}
	    	else {
	    		nbrOfBearishHaramiWithBadResult++;
	    		System.out.println("bad bearish result level 3 ");
	    	}
		 //---------------
	 }
    }
    
    
    //Bearish Hanging Man Pattern
    public void bearishHangingMan(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
        
        this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
    	// before one day
        this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
        
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
        //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
        /*-------------------------*/
    	shadowTopOfToDay=Math.abs(highPriceOfToday-bodyTopOfToday);
    	shadowBottomOfToDay=Math.abs(lowPriceOfToday-bodyBottomOfToday);
    	bearishHangingManRapport = Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(lowPriceBeforeOneDay-highPriceBeforeOneDay);
    if     (openPriceBeforeOneDay<closePriceBeforeOneDay 										&&
    		bearishHangingManRapport>0.6 														&&
    		highPriceOfToday>highPriceBeforeOneDay 												&&
    		shadowBottomOfToDay> 2* Math.abs(openPriceBeforeOneDay-closePriceBeforeOneDay) 	    &&
    		shadowTopOfToDay >0.3 * Math.abs(openPriceBeforeOneDay-closePriceBeforeOneDay))
    { 
    	nbrTotalOfBearishHangingManPattern++;
    	LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    	 System.out.println("**bearish Hanging Man pattern at:"+ldt);
    	//TEST---------------------
		 if(openPriceAfterOneDay>closePriceAfterOneDay) {
	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay || closePriceAfterTwoDay<=openPriceAfterOneDay) {
	    			nbrOfBearishHangingManWithGoodResult++;
	    			//System.out.println("good bearish result level 1");
	    		}
	    		else 
	    		{
	    			nbrOfBearishHangingManWithBadResult++;
	    		//	System.out.println("bad bearish result level 1");
	    		}
	    		
	    		}
	    	else if (openPriceAfterOneDay<=openPriceOfToDay)
	    	{
	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay)
 	    		{

	    			nbrOfBearishHangingManWithGoodResult++;
	    			//System.out.println("good bearish result level 2");
 	    		}
 	    		else
 	    			{
 	    			nbrOfBearishHangingManWithBadResult++;
 	    			//System.out.println("bad bearish result level 2");
 	    			
 	    			}
	    		
	    	}
	    	else {
	    		nbrOfBearishHangingManWithBadResult++;
	    	//	System.out.println("bad bearish result level 3 ");
	    	}
		 //---------------
        	
    }
    }
    
  //Bearish Engulfing Pattern
    public void bearishEngulfing(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
        
        this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
    	// before one day
        this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
        
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
        //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
        /*-------------------------*/
    	bearishEngulfingRapport =Math.abs(openPriceOfToDay -closePriceOfToday) /Math.abs(highPriceOfToday-lowPriceOfToday);
    	 if(openPriceOfToDay<closePriceOfToday) { //green candleStick of to day
    		 bodyBottomOfToday =openPriceOfToDay;
    		 bodyTopOfToday = closePriceOfToday;
    		 }
    	 else {                                   //red candleStick of to day
    		 bodyBottomOfToday=closePriceOfToday;
    		 bodyTopOfToday=openPriceOfToDay;
    	 } 
    	if(openPriceBeforeOneDay<closePriceBeforeOneDay) { //green candleStick before one day
    		 bodyBottomBeforeOneDay =openPriceBeforeOneDay;
    		 bodyTopBeforeOneDay = closePriceBeforeOneDay;
    		 }
    	 else {                                   //red candleStick before one day
    		 bodyBottomBeforeOneDay=closePriceBeforeOneDay;
    		 bodyTopBeforeOneDay=openPriceBeforeOneDay;
    	 }
    	if(openPriceBeforeOneDay<closePriceBeforeOneDay 	&&
    			openPriceOfToDay>closePriceOfToday 			&&
    			bodyBottomOfToday<bodyBottomBeforeOneDay 	&&
    			bodyTopOfToday<bodyTopBeforeOneDay 			&&
    			bearishEngulfingRapport>0.6)
    	{
    		nbrTotalOfBearishEngulfingPattern++;
    		LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    		System.out.println("we have Bearish Engulfing Pattern at: "+ldt);
    		//TEST---------------------
	   		 if(openPriceAfterOneDay>closePriceAfterOneDay) {
	   	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay || closePriceAfterTwoDay<=openPriceAfterOneDay) {
	   	    			nbrOfBearishEngulfingWithGoodResult++;
	   	    			System.out.println("good bearish result level 1");
	   	    		}
	   	    		else 
	   	    		{
	   	    			nbrOfBearishEngulfingWithBadResult++;
	   	    		System.out.println("bad bearish result level 1");
	   	    		}
	   	    		
	   	    		}
	   	    	else if (openPriceAfterOneDay<=openPriceOfToDay)
	   	    	{
	   	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay)
	    	    		{

	   	    			nbrOfBearishEngulfingWithGoodResult++;
	   	    			System.out.println("good bearish result level 2");
	    	    		}
	    	    		else
	    	    			{
	    	    			nbrOfBearishEngulfingWithBadResult++;
	    	    			System.out.println("bad bearish result level 2");
	    	    			
	    	    			}
	   	    		
	   	    	}
	   	    	else {
	   	    		nbrOfBearishEngulfingWithBadResult++;
	   	    		System.out.println("bad bearish result level 3 ");
	   	    	}
	   		 //---------------
    }
    }
    
    //Bearish Evening Star Pattern
    public void bearishEveningStar(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+2).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+2).getHigh());
        this.openPriceOfToDay =Double.parseDouble(candlesticks.get(i+2).getOpen());
        this.closePriceOfToday =Double.parseDouble(candlesticks.get(i+2).getClose());
    	// before one day
        this.openPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getClose());
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i+1).getHigh());
        //before two day
        this.openPriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getClose());
        this.lowPriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeTwoDay =Double.parseDouble(candlesticks.get(i).getHigh());
        //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+4).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+4).getOpen());
        /*-------------------------*/
    	bearishEveningStarRapport1 =Math.abs(openPriceBeforeTwoDay -closePriceBeforeTwoDay) /Math.abs(lowPriceBeforeTwoDay-highPriceBeforeTwoDay);
    	bearishEveningStarRapport2 =Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(lowPriceBeforeOneDay-highPriceBeforeOneDay);	
    	if(openPriceBeforeTwoDay<closePriceBeforeTwoDay                                                                  &&
    			openPriceOfToDay>closePriceOfToday 																		 &&
    			bearishEveningStarRapport1>0.6 																			 &&
    			openPriceBeforeOneDay>closePriceBeforeTwoDay 															 &&
    			openPriceOfToDay<closePriceBeforeOneDay 																 &&
    			bearishEveningStarRapport2<0.3 																			 &&
    			(openPriceBeforeOneDay -closePriceBeforeOneDay)<Math.abs(openPriceBeforeTwoDay -closePriceBeforeTwoDay)  &&
    			(openPriceBeforeOneDay -closePriceBeforeOneDay)<Math.abs(openPriceOfToDay -closePriceOfToday) 			 &&
    			highPriceBeforeOneDay>highPriceOfToday 																	 &&
    			highPriceBeforeOneDay>highPriceBeforeTwoDay 															 &&
    			lowPriceBeforeOneDay>openPriceBeforeTwoDay 																 &&
    			lowPriceBeforeOneDay>closePriceOfToday)
    	{
    		nbrTotalOfBearishEveningStarPattern++;
   		LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+2).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

    	System.out.println("++Bearish Evening Star Pattern at:"+ldt);
    		//TEST---------------------
	   		 if(openPriceAfterOneDay>closePriceAfterOneDay) {
	   	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay || closePriceAfterTwoDay<openPriceAfterOneDay) {
	   	    			System.out.println(closePriceAfterTwoDay<openPriceAfterOneDay);
	   	    			nbrOfBearishEveningStarWithGoodResult++;
	   	    			System.out.println("good bearish result level 15");
	   	    		}
	   	    		else 
	   	    		{
	   	    			nbrOfBearishEveningStarWithBadResult++;
	   	    			System.out.println("bad bearish result level 1");
	   	    		}
	   	    		
	   	    		}
	   	    	else if (openPriceAfterOneDay<=openPriceOfToDay)
	   	    	{
	   	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay)
	    	    		{

	   	    			nbrOfBearishEveningStarWithGoodResult++;
	   	    			System.out.println("good bearish result level 2");
	    	    		}
	    	    		else
	    	    			{
	    	    			nbrOfBearishEveningStarWithBadResult++;
	    	    			System.out.println("bad bearish result level 2");
	    	    			
	    	    			}
	   	    		
	   	    	}
	   	    	else {
	   	    		nbrOfBearishEveningStarWithBadResult++;
	   	    		System.out.println("bad bearish result level 3 ");
	   	    	}
	   		 //---------------
    		
    	}
    }
    // Bearish Gravestone Doji Pattern
    public void bearishGravestoneDoji(int i) {
    	//to day
        this.lowPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getLow());
        this.highPriceOfToday =Double.parseDouble(candlesticks.get(i+1).getHigh());
        
        this.openPriceOfToDay = Double.parseDouble(candlesticks.get(i+1).getOpen());
        this.closePriceOfToday = Double.parseDouble(candlesticks.get(i+1).getClose());
    	// before one day
        this.openPriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getOpen());
        this.closePriceBeforeOneDay = Double.parseDouble(candlesticks.get(i).getClose());
        
        this.lowPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getLow());
        this.highPriceBeforeOneDay =Double.parseDouble(candlesticks.get(i).getHigh());
        //after one day
    	this.closePriceAfterOneDay =Double.parseDouble(candlesticks.get(i+2).getClose());
    	this.openPriceAfterOneDay=Double.parseDouble(candlesticks.get(i+2).getOpen());
    	//after two days
    	this.closePriceAfterTwoDay =Double.parseDouble(candlesticks.get(i+3).getClose());
    	this.openPriceAfterTwoDay=Double.parseDouble(candlesticks.get(i+3).getOpen());
        /*-------------------------*/
    	bearishGravestoneDojiRapport= Math.abs(openPriceBeforeOneDay -closePriceBeforeOneDay) /Math.abs(lowPriceBeforeOneDay-highPriceBeforeOneDay);
    	if(openPriceBeforeOneDay<closePriceBeforeOneDay &&
    			bearishGravestoneDojiRapport>0.6 &&
    			highPriceOfToday>highPriceBeforeOneDay &&
    			(highPriceOfToday -closePriceOfToday)> 3*Math.abs(openPriceOfToDay-closePriceOfToday) &&
    			(openPriceOfToDay-lowPriceOfToday)< (highPriceOfToday-closePriceOfToday)/3
    			)
    	{
    		nbrTotalOfBearishGravestoneDojiPattern++;
    		LocalDateTime ldt = Instant.ofEpochMilli(candlesticks.get(i+1).getOpenTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

    		System.out.println("/ Bearish Gravestone Doji Pattern at:"+ldt);	
    		
        		//TEST---------------------
    		
   	   		 if(openPriceAfterOneDay>closePriceAfterOneDay) 
   	   		      {
   	   	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay || closePriceAfterTwoDay<=openPriceAfterOneDay) 
   	   	    		{
   	   	    		nbrOfBearishGravestoneDojiWithGoodResult++;
   	   	    		}
   	   	    		else 
   	   	    		{
   	   	    		nbrOfBearishGravestoneDojiWithBadResult++;
   	   	    		}
   	   	    		
   	   	    	  }
   	   	    else if (openPriceAfterOneDay<=openPriceOfToDay)
   	   	    	  {
   	   	    		if(openPriceAfterTwoDay>closePriceAfterTwoDay)
   	    	    		{
   	   	    		     nbrOfBearishGravestoneDojiWithGoodResult++;
   	    	    		}
   	    	    	else
   	    	    		{
   	    	    		nbrOfBearishGravestoneDojiWithBadResult++;
   	    	    		}
   	   	    	  }
   	   	    else {
   	   	    	 nbrOfBearishGravestoneDojiWithBadResult++;
   	   	    	 }
   	   		 
   	   		 
   	   		 //---------------
    	}
    }
    //---------------------------------------------------Partie Test--------------------------------------------------------------------
    public void  analyse() {
    	
    	List<Candlestick> list = new ArrayList<>();
    	
    	for (int i = 0; i <candlesticks.size()-5 ; i++) {
            bullishEngulfing(i);
            bullishHarami(i); 
            bullishDoji(i);
            bullishHammer(i);
            bullishMoningStar(i); 
           
            bearishHarami(i);   
            bearishHangingMan(i);
            bearishEngulfing(i);
            bearishEveningStar(i);
            bearishGravestoneDoji(i);
    	}
    	try {
    	System.out.println("\t"+"----------------Analyse des Results-----------");
    	System.out.println("1----Bullish Engulfing results---");
    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBullishEngulfingWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBullishEngulfingWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBullishEngulfingPattern);
    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBullishEngulfingWithGoodResult *100/nbrTotalOfBullishEngulfingPattern)+"%" );
    	 res.put("bullishEngulfing", (nbrOfBullishEngulfingWithGoodResult *100/nbrTotalOfBullishEngulfingPattern));
    	}
    	catch(RuntimeException e) {
    		System.out.println("il n'existe pas ce pattern");
    	}
    	try {
    	System.out.println("2----Bullish Harami results---");
    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBullishHaramiWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBullishHaramiWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBullishHaramiPattern);
    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBullishHaramiWithGoodResult *100/nbrTotalOfBullishHaramiPattern)+"%" );
    	res.put("bullishHarami", (nbrOfBullishHaramiWithGoodResult *100/nbrTotalOfBullishHaramiPattern));
   	 
    }
    catch(RuntimeException e) {
    	System.out.println("il n'existe pas ce pattern");
    }
    try {
    	System.out.println("3----Bullish Doji results---");
    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBullishDojiWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBullishDojiWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBullishDojiPattern);
    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBullishDojiWithGoodResult *100/nbrTotalOfBullishDojiPattern)+"%" );
    	res.put("bullishDoji", (nbrOfBullishDojiWithGoodResult *100/nbrTotalOfBullishDojiPattern));
   	 
    }
    catch(RuntimeException e) {
    	System.out.println("il n'existe pas ce pattern");
    }
    
    
	 try {
	    	System.out.println("4----Bullish Hammer results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBullishHammerWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBullishHammerWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBullishHammerPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBullishHammerWithGoodResult *100/nbrTotalOfBullishHammerPattern)+"%" );
	    	
	    	 res.put("bullishHammer", (nbrOfBullishHammerWithGoodResult *100/nbrTotalOfBullishHammerPattern));
	    		   
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 
	 
	 
	 try {
	    	System.out.println("5----Bullish MorningStar results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBullishMoningStarWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBullishMoningStarWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBullishMoningStarPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBullishMoningStarWithGoodResult *100/nbrTotalOfBullishMoningStarPattern)+"%" );
	    	 res.put("bullishMoningStar", (nbrOfBullishMoningStarWithGoodResult *100/nbrTotalOfBullishMoningStarPattern));
	    		  
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 try {
	    	System.out.println("6----Bearish Harami results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBearishHaramiWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBearishHaramiWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBearishHaramiPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBearishHaramiWithGoodResult *100/nbrTotalOfBearishHaramiPattern)+"%" );
	    	res.put("bearishHarami", (nbrOfBearishHaramiWithGoodResult *100/nbrTotalOfBearishHaramiPattern));
	   	    
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 try {
	    	System.out.println("7----Bearish HangingMan results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBearishHangingManWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBearishHangingManWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBearishHangingManPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBearishHangingManWithGoodResult *100/nbrTotalOfBearishHangingManPattern)+"%" );
	    	res.put("bearishHangingMan",(nbrOfBearishHangingManWithGoodResult *100/nbrTotalOfBearishHangingManPattern) );
	   	    
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 try {
	    	System.out.println("8----Bearish Engulfing results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBearishEngulfingWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBearishEngulfingWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBearishEngulfingPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBearishEngulfingWithGoodResult *100/nbrTotalOfBearishEngulfingPattern)+"%" );
	    	res.put("bearishEngulfing", (nbrOfBearishEngulfingWithGoodResult *100/nbrTotalOfBearishEngulfingPattern));
	   	   
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 try {
	    	System.out.println("9----Bearish EveningStar results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBearishEveningStarWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBearishEveningStarWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBearishEveningStarPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBearishEveningStarWithGoodResult *100/nbrTotalOfBearishEveningStarPattern)+"%" );
	    	res.put("bearishEveningStar", (nbrOfBearishEveningStarWithGoodResult *100/nbrTotalOfBearishEveningStarPattern));
	    	  
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 try {
	    	System.out.println("10----Bearish Gravestone Doji results---");
	    	System.out.println("nbrPatternWithGoodResult:"+nbrOfBearishGravestoneDojiWithGoodResult+"\n"+"nbrPatterWithBadResult:"+nbrOfBearishGravestoneDojiWithBadResult+"\n"+"nbrTotalOfPattern:"+nbrTotalOfBearishGravestoneDojiPattern);
	    	System.out.println("Pourcentage d'exactidute de ce pattern ="+(nbrOfBearishGravestoneDojiWithGoodResult *100/nbrTotalOfBearishGravestoneDojiPattern)+"%" );
	    	
	    	res.put("bearishGravestoneDoji",(nbrOfBearishGravestoneDojiWithGoodResult *100/nbrTotalOfBearishGravestoneDojiPattern));
	    		   
	 }
	    catch(RuntimeException e) {
	    	System.out.println("il n'existe pas ce pattern");
	    }
	 
	 
	System.out.println("Before Analyse");
	 res.forEach((key, value) -> {
		 
		 System.out.println(key + ":" + value+"%");
		 
	     });
	 
    }
   
    public HashMap<String, Integer> filterPatternAfterTestWithCouple(String s ,CandlestickInterval i,int beforeNumberOfDay) {
    	
    	candlesticks = client.getCandlestickBars(s, i);
    	candlesticks= candlesticks.subList(candlesticks.size()-beforeNumberOfDay, candlesticks.size()-1);
    	System.out.println(candlesticks.size());

        analyse();
       System.out.println("After Analyse");
   	    res.forEach((key, value) -> {
   		 if (value>=80) 
   		 {
   		 System.out.println(key + ":" + value+"%");
   		 nvMap.put(key, value);
   		 }
   	     });
     	alertUser();
   	return nvMap;
    }
    
    public void alertUser() {
    	nvMap.forEach((key,value)->{
    		switch (key) {
    		case "bullishEngulfing": System.err.println("alert bullishEngulfing");break;
    		case "bullishHarami": System.err.println("alert bullishHarami");break;
    		case "bullishDoji": System.err.println("alert bullishDoji");break;
    		case "bullishHammer": System.err.println("alert bullishHammer");break;
    		case "bullishMoningStar": System.err.println("alert bullishMoningStar");break;
    		
    		case "bearishHarami": System.err.println("alert bearishHarami");break;
    		case "bearishHangingMan": System.err.println("alert bearishHangingMan");break;
    		case "bearishEngulfing": System.err.println("alert bearishEngulfing");break;
    		case "bearishEveningStar": System.err.println("alert bearishEveningStar");break;
    		case "bearishGravestoneDoji": System.err.println("alert bearishGravestoneDoji");break;
    		}  
          
    	});
    }
    
}