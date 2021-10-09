import java.util.ArrayList;
import java.util.List;

public class Posting {


  private int docf;
  private List<documentTfTuple> postingList = new ArrayList<documentTfTuple>();

  public Posting(int docf,List<documentTfTuple> postingList) {
    this.docf = docf;
    this.postingList = postingList;
  }

  public int getDocf(){
    return docf;
  }

  public void setDocf(int docf){
    this.docf = docf;
  }

  public void setPostingList(List<documentTfTuple> postingList){
    this.postingList = postingList;
  }

  public List<documentTfTuple> getPostingList(){
    return postingList;
  }
}
