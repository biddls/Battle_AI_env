package FerrantiM1;

public class Matrix {
    public int rows;
    public int cols;
    public float[][] arr;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.arr = new float[rows][cols];
    }

    public void fillMatrix(int row, int col, float value){
        arr[row][col] = value;
    }

    void multiply(Matrix mat) throws Exception {
        if(cols == mat.rows){
            float[][] temp = new float[rows][mat.cols];
            for(int row = 0; row < rows; row++) {
                for(int col = 0; col < mat.cols; col++) {
                    for(int rowin = 0; rowin < mat.rows; rowin++) {
                        for(int colin = 0; colin < cols; colin++) {
                            temp[row][col] += arr[rowin][colin];
                        }
                    }
                }
            }
            arr = temp;
        }
        throw new Exception("Matrices are the wrong size");
    }

    void add(Matrix mat) throws Exception {
        if (cols == mat.cols && rows == mat.rows) {
            float[][] temp = new float[rows][mat.cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    temp[row][col] = arr[row][col] + mat.arr[row][col];
                }
            }
            arr = temp;
        }
        throw new Exception("Matrices are the wrong size");
    }
}
