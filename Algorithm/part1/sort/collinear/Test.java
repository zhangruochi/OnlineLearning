public class Test{

    private static class Student{
        public int id;
        public Student(int i){
            id = i;
        }

        public String toString(){
            return  "student "+ id;
        }
    }


    private static void swap(Student[] students, int i){
         Student tmp = students[0];
         students[0] = students[i];
         students[i] = tmp;
    } 

    public static void main(String[] args) {


        Student[] students = new Student[]{new Student(0),new Student(1), new Student(2)};
        for(int i=0;i<3;i++){
            System.out.println(students[i]);
        }

        swap(students,1);

        for(int i=0;i<3;i++){
            System.out.println(students[i]);
        }






    }
}