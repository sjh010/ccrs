function pad(n, digits){
	var zeros = '';
	n = n.toString();
	
	if(n.length < digits){
		for(var i=0; i<digits-n.length; i++)
			zeros += '0';
	}
	return zeros + n;
}