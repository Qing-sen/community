package q.community.community.dto;

import lombok.Data;
import q.community.community.model.Question;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions ;
    private boolean showPrevious;//显示上一页
    private boolean showFirstPage;//显示到第一页
    private boolean showNext;//显示下一个
    private boolean showEndPage;//显示最后一页
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public int setpagination(Integer totalCount, Integer page, Integer size) {
        //总页数模除每页个数，如果能除尽，页数就等于商
        //如果除不尽 页数就等于商+1
        //求总页数
        if(totalCount % size==0){
            totalPage=totalCount / size;
        }else{
            totalPage=totalCount / size+1;
        }

        //当在地址栏中输入的页码为负数或者超过最大页码数时，进行进行处理
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        //给当前类中的page赋值
        this.page=page;

        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page - i > 0) {
                pages.add(0,page - i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }



        //判断是否显示上一页
        if(page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        //判断是否显示下一页
        if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //判断是否显示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //判断是否显示最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }





        return totalPage;
    }
}
