import java.util.List;

public class OptimalThings<T extends ComparableThing<T>>{
    public List<T> a;


    public OptimalThings (List<T> e) {
        a=e;
    }

    public T min(){
        T smallest=a.get(0);
       for (int i = 1; i < a.size(); i++) {
            if(a.get(i).isLessThan(smallest)){
                smallest=a.get(i);
            }
        }

        return smallest;
    }

    public T max(){
        T biggest=a.get(0);
        for (int i = 0; i < a.size(); i++) {
            if(a.get(i).isLessThan(biggest)){
                continue;
            }3
            biggest=a.get(i);
        }

        return biggest;
    }
    public T min(ThingComparator tc){

        return null;
    }

    public T max(ThingComparator tc){
        return null;
    }

    public List pareto(ThingComparator tc){
        return null;
    }

}
