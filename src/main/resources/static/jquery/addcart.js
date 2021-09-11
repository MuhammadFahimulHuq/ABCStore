$( document ).ready(function() {
        $("#modal-success").hide();
        $("#modal-failure").hide();
        $("#addTocart").on("click",function(e){
         addQuantity();
        });
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
       $("#modal-success").text(response).show();
       }).fail(function(){
       $("#modal-failure").text(response).show();
       })
}


