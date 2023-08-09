const handlePrint = () => {
   var actContents = document.body.innerHTML;
   document.body.innerHTML = actContents;
   window.print();
}