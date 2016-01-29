package pagerank;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class PageRanker {
	
	public double[][] getNormalizedP(double [][] P, double alpha){
		int n = P.length;
		double n1 = 1.0*1/n;
		double ans[][] = new double[n][n];
		for(int i = 0; i < n; i++){
			ans[i] = new double[n];
			double sum = 0;
			for(int j = 0; j < n; j ++)
				sum += P[i][j];
			for(int j = 0; j < n; j ++)
				if(sum == 0)
					ans[i][j] = n1;
				else
					ans[i][j] = n1 * alpha + P[i][j] * (1 - alpha) / sum;
		}
		return ans;
	}
	
	public void print(double P[][]){
		int n = P.length;
		for(int i = 0; i < n; i ++){
			for(int j = 0; j < n; j ++){
				System.out.print(P[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public void normalize(RealMatrix a){
		double sum = length(a);
		int s = a.getColumnDimension();
		for (int i = 0; i < s; i++)
			a.setEntry(0, i, a.getEntry(0, i)/sum);
	}
	
	public double length(RealMatrix a){
		double sum = 0;
		int s = a.getColumnDimension();
		for (int i = 0; i < s; i++)
			sum += a.getEntry(0, i) * a.getEntry(0, i);
		sum = Math.sqrt(sum);
		return sum;
	}
	
	public double[] pageRank(double [][] P, double alpha, double threashold){
		double [][] nP = getNormalizedP(P, alpha);
		int s = nP.length;
		RealMatrix cof = new Array2DRowRealMatrix(nP);
		RealMatrix a = MatrixUtils.createRealMatrix(1, s);
		for (int i = 0; i < s; i++)
			a.setEntry(0, i, 1.0/s);
		RealMatrix diff, tmp = a;
		do{
			a = tmp;
			tmp = a.multiply(cof);
			diff = tmp.subtract(a);
		}while(length(diff) > threashold);
		return a.getRow(0);	
	}

}
