$(document).ready(function(){

  $(".minusButton").on("click", function(evt){
  evt.preventDefault();
  decreaseQuantity($(this));
  });
    $(".plusButton").on("click", function(evt){
    evt.preventDefault();
    increaseQuantity($(this));
    });

       updateTotal();
});

function decreaseQuantity(link){
  productId= link.attr("id");
  qtyInput = $("#quantity"+productId);
  newQty = parseInt(qtyInput.val())-1;
  if(newQty>0) {qtyInput.val(newQty);
  updateQuantity(productId,newQty);}
}
function increaseQuantity(link){
    productId= link.attr("id");
    qtyInput = $("#quantity"+productId);
    newQty = parseInt(qtyInput.val())+1;
    if(newQty<21) {qtyInput.val(newQty);
    updateQuantity(productId,newQty);}
}
function updateQuantity(productId,quantity){
    var token = $("meta[name='_csrf']").attr("content");
       var header = $("meta[name='_csrf_header']").attr("content");
       url=  "/cart/update/"+productId+"/"+quantity;
       $.ajax({
       type: "POST",
       url: url,
       beforeSend: function(xhr){
       xhr.setRequestHeader(header,token);
       }
       }).done(function(newSubTotal){
        updateSubTotal(newSubTotal,productId);
        updateTotal();

       }).fail(function(){
        $("#modal").text("Error");
       })
}
function updateSubTotal(newSubTotal,productId){
$("#subTotal"+productId).text(newSubTotal);

}

function updateTotal(){

total = 0.0;

$(".productSubTotal").each(function(index,element){
total = total + parseFloat(element.innerHTML);
});
$("#totalAmount").text(total);
return total;
}

