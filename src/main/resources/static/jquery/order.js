$(document).ready(function(){
updateTotal();
updateTotalWithShipment();
$("#totalWithShipmentCost").text('*Choose From Below')

})


function updateTotal(){

total = 0.0;

$(".productSubTotal").each(function(index,element){
total = total + parseFloat(element.innerHTML);
});
$("#subTotalAmount").text(total);
return total;
}

function updateTotalWithShipment(){
$(".radioPayments").on("input",function(event){
event.preventDefault();
paymentId = $(this).attr("id");
price = $('#'+paymentId).val();
total = parseFloat(updateTotal())+parseFloat(price);
$(".totalWithShipmentCost").text(total);
$(".totalWithShipmentCost").val(total);
})}