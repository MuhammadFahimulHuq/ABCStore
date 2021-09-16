$( document ).ready(function() {
        $("#addTocart").on("click",function(e){
         addQuantity();
        });

   $(".homeAddToCart").on("click",function(e){
   addDefaultQuantity($(this));
   })
});


function addQuantity(){
       var token = $("meta[name='_csrf']").attr("content");
       var header = $("meta[name='_csrf_header']").attr("content");

       quantity = $("#quantity" + productID).val();
       url=  "/cart/"+productID+"/"+quantity;
       $.ajax({
       type: "POST",
       url: url,
       beforeSend: function(xhr){
       xhr.setRequestHeader(header,token);
       }
       }).done(function(response){
      $.notify(response, "success");
       }).fail(function(){
   	  $.notify(response, "error");
       })
}
function addDefaultQuantity(link){
       var token = $("meta[name='_csrf']").attr("content");
       var header = $("meta[name='_csrf_header']").attr("content");

       productId= link.attr("id");
       url=  "/cart/"+productId+"/"+1;
       $.ajax({
       type: "POST",
       url: url,
       beforeSend: function(xhr){
       xhr.setRequestHeader(header,token);
       }
       }).done(function(response){
       $.notify(response, "success");
       }).fail(function(response){
   	  $.notify("Error while adding product to shopping cart.", "error");
       })

}

