package br.com.tiagoaramos.estoque.control;

public class Sort {
    
	private int[] array;
	
	public Sort(int[] array){
		this.array = array;
	}
	
	public int[] ordena(){
		int max, min;
		max = array.length -1;
		min = 0;
		return ordena(min,max);
	}
	
	private int[] ordena(int min, int max){
		
		if(min >= max)
			return array;
		 
		int pivo  = (min + max) / 2;
		
		if(min == pivo){
			troca(min,pivo+1);
		}else if (min == pivo - 1){
			troca(min,pivo);
		}else{
			array = ordena(min,pivo);
			array = ordena(pivo,max);			
		}
		
		troca(min,pivo);
		troca(pivo,max);
		
		return array;
	}

	private void troca(int i, int j) {
		if(array[i] > array[j]){			
			int aux = array[i];
			array[i] = array[j];
			array[j] = aux;		
		}
	}
	
	public static void main(String[] args){
		int[] array = {1,5,7,242,2,1,6,3,87,4,21,1,5,6,8,4,2,3,99,9,7,213,21,287,378,7};
		array = new Sort(array).ordena();
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}

}
