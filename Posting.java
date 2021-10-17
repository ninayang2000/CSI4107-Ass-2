import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Posting {


  private int docf;
  // private List<documentTfTuple> postingList = new ArrayList<documentTfTuple>();
  private Set<documentTfTuple> postingList = new HashSet<documentTfTuple>(); 

  public Posting(int docf,Set<documentTfTuple> postingList) {
    this.docf = docf;
    this.postingList = postingList;
  }

  public int getDocf(){
    return docf;
  }

  public void setDocf(int docf){
    this.docf = docf;
  }

  public void setPostingList(Set<documentTfTuple> postingList){
    this.postingList = postingList;
  }

  public Set<documentTfTuple> getPostingList(){
    return postingList;
  }
}
