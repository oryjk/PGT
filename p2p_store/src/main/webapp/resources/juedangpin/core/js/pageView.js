
function gotoPageNum(num) {

	   if(num==null){
		    
		  num= $("#pageInputNum").val();
		  	    		   
	   }
	   
	 
	   
	   var pageSize = $("#capacity_comm").val();
	   
	   var totalpage = $("#totalPage").val();
	   
	   if(parseInt(num)> parseInt(totalpage)){
			  
			 num= totalpage;
			  
		  }
	   
	   if(parseInt(num)<1){
		   num=1;
	   }
     var currentIndex = (num-1) * pageSize;
    
     if ( $("#pageViewFrom").length > 0 ) { 
     
     $("#pageViewFrom").append("<input type='hidden' name='currentIndex' value='"+currentIndex+"'/> ");	
     
     $("#pageViewFrom").submit();
     
     }
     
     if ( $("#pageViewFromRestful").length > 0 ) { 
    	 
     
    	var href= $('#pageViewFromRestful').val();
    	 
    	var newhref=href+"/"+currentIndex;
    	
    	window.location.href=newhref;
     }
    
}



