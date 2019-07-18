package sortowanie;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class sorting {
	
	private static int comparisons = 0;
	private static int shifts = 0;
	private static boolean desc = false;
	private static boolean stat = false;
	
	public static void main(String[] args) {
		
		int type = 0;
		String file = "";
		int times = 0;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("--type")) {
				i++;
				if(args[i].equals("select")) {
					type = 1;
				} else if(args[i].equals("insertion")) {
					type = 2;
				} else if(args[i].equals("heap")) {
					type = 3;
				} else if(args[i].equals("quick")) {
					type = 4;
				} else if(args[i].equals("mquick")) {
					type = 5;
				} 
			} else if(args[i].equals("--asc")) {
				desc = false;
			} else if(args[i].equals("--desc")) {
				desc = true;
			} else if(args[i].equals("--stat")) {
				stat = true;
				i++;
				file = args[i];
				i++;
				times = Integer.parseInt(args[i]);
			}
		}
		
		if(stat) {
			Random generator = new Random();
			PrintWriter save = null;
			long startTime = 0;
			long endTime   = 0;
			try {
				save = new PrintWriter(file+".txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
			for(int n = 100; n <= 10000; n += 100) {
				System.out.println(n);
				int arr[] = new int[n];
				for(int k = 0; k < times; k++) {
					for(int i = 0 ; i < n; i++) {
						arr[i] = generator.nextInt(2*n);
					}
					
					comparisons = 0;
					shifts = 0;
					int arr1[] = arr.clone();
					startTime = System.nanoTime();
					SelectSort(arr1);
					endTime   = System.nanoTime();
					long totalTime = endTime - startTime;
					save.print(n+";"+comparisons+";"+shifts+";"+(double)totalTime/1000000+";");
					
					comparisons = 0;
					shifts = 0;
					arr1 = arr.clone();
					startTime = System.nanoTime();
					InsertionSort(arr1,0,arr.length-1);
					endTime = System.nanoTime();
					totalTime = endTime - startTime;
					save.print(comparisons+";"+shifts+";"+(double)totalTime/1000000+";");
					
					comparisons = 0;
					shifts = 0;
					arr1 = arr.clone();
					startTime = System.nanoTime();
					HeapSort(arr1);
					endTime = System.nanoTime();
					totalTime = endTime - startTime;
					save.print(comparisons+";"+shifts+";"+(double)totalTime/1000000+";");
					
					comparisons = 0;
					shifts = 0;
					arr1 = arr.clone();
					startTime = System.nanoTime();
					QuickSort(arr1,0,n-1);
					endTime = System.nanoTime();
					totalTime = endTime - startTime;
					save.print(comparisons+";"+shifts+";"+(double)totalTime/1000000+";");
					
					comparisons = 0;
					shifts = 0;
					arr1 = arr.clone();
					startTime = System.nanoTime();
					ModifiedQuickSort(arr1,0,arr.length-1);
					endTime   = System.nanoTime();
					totalTime = endTime - startTime;
					save.print(comparisons+";"+shifts+";"+(double)totalTime/1000000+";");
					save.println("");
				}
			}
			save.close();
		} else {
			Scanner scanner = new Scanner(System.in);
			int n = scanner.nextInt();
			int arr[] = new int[n];
			for(int i = 0; i < n; i++) {
				arr[i] = scanner.nextInt();
			}
			
			long startTime = 0;
			long endTime   = 0;
			
			if(type == 0) {
				System.err.println("Incorrect sorting type!");
				System.exit(0);
			} else if(type == 1) {
				startTime = System.nanoTime();
				SelectSort(arr);
				endTime   = System.nanoTime();
			} else if (type == 2) {
				startTime = System.nanoTime();
				InsertionSort(arr,0,arr.length-1);
				endTime   = System.nanoTime();
			} else if (type == 3) {
				startTime = System.nanoTime();
				HeapSort(arr);
				endTime   = System.nanoTime();
			} else if (type == 4) {
				startTime = System.nanoTime();
				QuickSort(arr,0,arr.length-1);
				endTime   = System.nanoTime();
			} else if (type == 5) {
				startTime = System.nanoTime();
				ModifiedQuickSort(arr,0,arr.length-1);
				endTime   = System.nanoTime();
			}
				
			System.err.println("Number of comparisons: "+comparisons);
			System.err.println("Number of shifts: "+shifts);
			long totalTime = endTime - startTime;
			System.err.println("Total time: "+(double)totalTime/1000000);
			
			if(!CheckCorrectness(arr)) {
				System.err.println("Error!");
				System.exit(0);
			}
				
			System.out.println("Number of elements: "+arr.length);
			System.out.print("Sorted elements: ");
			for(int i=0;i<arr.length;i++) {
				System.out.print(arr[i]+" ");
			} 
			scanner.close();
		}
	}
	
	private static void SelectSort(int arr[]) {
        for (int i = 0; i < arr.length-1; i++) { 
            int min_idx = i; 
            for (int j = i+1; j < arr.length; j++) {
            	if (compare(arr[min_idx],arr[j])) {
                	min_idx = j; 
                }
            }
            swap(arr,min_idx,i);
        } 
	}
	
	private static void InsertionSort(int arr[], int low, int high) {
		for (int i = low + 1; i <= high; ++i) { 
            int key = arr[i]; 
            int j = i - 1; 
            while (j >= low && compare(arr[j],key)) {
            		shifts++;
                    arr[j + 1] = arr[j]; 
                    j = j - 1; 
            } 
            shifts++;
            arr[j + 1] = key; 
        } 
	}
	
	private static void HeapSort(int arr[])  { 
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
        	heapify(arr, arr.length, i);
        }
        for (int i = arr.length-1; i>=0; i--)  {
        	swap(arr,i,0); 
            heapify(arr, i, 0); 
        } 
    } 
    private static void heapify(int arr[], int n, int i) { 
        int largest = i;
        int left = 2*i + 1;
        int right = 2*i + 2;
        
        if (left < n) {
        	if (compare(arr[left],arr[largest])) {
            	largest = left; 
            }
        }
        if(right < n) {
        	if (compare(arr[right],arr[largest])) {
            	largest = right; 
            }
        }
        if (largest != i)  { 
        	swap(arr,i,largest);
            heapify(arr, n, largest); 
        } 
    } 
  
    private static void QuickSort(int arr[], int low, int high) { 
        int pivot = arr[( low + high ) / 2];
        int i = low;
        int j = high;
        while (i <= j) {
            while (compare(pivot,arr[i])) {
                i++;
            }
            while (compare(arr[j],pivot)) {
                j--;
            }
            if (i <= j) {
            	swap(arr,i,j);
                i++;
                j--;
            }
        }
        if (low < j) {
        	QuickSort(arr, low, j);
        }
        if (i < high) {
        	QuickSort(arr, i, high);
        }
    } 
    
    private static void ModifiedQuickSort(int arr[], int low, int high) {
        int middle = ( low + high ) / 2;
        if(compare(arr[low],arr[middle])) {
        	swap(arr,low,middle);
        }
        if(compare(arr[low],arr[high])) {
        	swap(arr,low,high);
        }
        if(compare(arr[middle],arr[high])) {
        	swap(arr,high,middle);
        }
        int pivot = arr[middle];
        int i = low;
        int j = high;
        while (i <= j) {
            while (compare(pivot,arr[i])) {
                i++;
            }
            while (compare(arr[j],pivot)) {
                j--;
            }
            if (i <= j) {
            	swap(arr,i,j);
                i++;
                j--;
            }
        }
        if (low < j) {
        	if(j - low <= 15) {
        		InsertionSort(arr, low, j);
        	} else {
        		ModifiedQuickSort(arr, low, j);
        	}
        }
        if (i < high) {
        	if(high - i <= 15) {
        		InsertionSort(arr, i, high);
        	} else {
        		ModifiedQuickSort(arr, i, high);
        	}
        }
    }
    
    private static void swap(int arr[], int i, int j) {
    	if(!stat) {
    		System.err.println("Shift: "+arr[i]+" "+arr[j]);
    	}
    	shifts++;
    	int temp = arr[i]; 
    	arr[i] = arr[j]; 
    	arr[j] = temp;
    }
    
    private static boolean compare(int a, int b) {
    	if(!stat) {
    		System.err.println("Compare: "+a+" "+b);
    	}
    	comparisons++;
    	if(desc) {
    		return a < b;
    	} else {
    		return a > b;
    	}
    }
    
    private static boolean CheckCorrectness(int arr[]) {
		if(desc) {
			for(int i = 1; i < arr.length; i++) {
				if(arr[i-1] < arr[i]) {
					return false;
				}
			}
		} else {
			for(int i = 1; i < arr.length; i++) {
				if(arr[i-1] > arr[i]) {
					return false;
				}
			}
		}
		return true;
	}
}