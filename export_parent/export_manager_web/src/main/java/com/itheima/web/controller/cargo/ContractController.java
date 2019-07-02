package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.service.cargo.ContractService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 购销合同的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController{

    @Reference
    private ContractService contractService;

    /**
     * 获取购销合同列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        //创建查询条件对象
        ContractExample example = new ContractExample();
        //添加排序条件
        example.setOrderByClause("create_time desc");
        //设置查询条件：企业id等于当前登录用户的企业id
        example.createCriteria().andCompanyIdEqualTo(super.getCurrentUserCompanyId());
        //1.调用service查询
        PageInfo pageInfo = contractService.findAll(example,page,size);
        //2.存入请求域中
        request.setAttribute("page",pageInfo);
        //3.前往购销合同的列表页面
        return "cargo/contract/contract-list";
    }

    /**
     * 前往购销合同的添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "cargo/contract/contract-add";
    }

    /**
     * 保存或者更新
     * @param contract
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Contract contract){
        //1.判断当前是保存还是更新
        if(UtilFuns.isEmpty(contract.getId())){
            //保存
            //设置企业id和名称
            contract.setCompanyId(super.getCurrentUserCompanyId());
            contract.setCompanyName(super.getCurrentUserCompanyName());
            contractService.save(contract);
        }else {
            //更新
            contractService.update(contract);
        }

        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 前往编辑页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询购销合同
        Contract contract = contractService.findById(id);
        //2.存入请求域中
        request.setAttribute("contract",contract);
        //3.前往编辑页面
        return "cargo/contract/contract-update";
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 提交
     * 把草稿状态购销合同改为 已上报，待报运
     * 购销合同的状态：
     *    0:草稿
     *    1：已上报
     *    2：已报运
     *    3：已装船
     *    4：已交货
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id){
        //1.创建一个购销合同的对象
        Contract contract = new Contract();
        //2.设置购销合同的id和状态
        contract.setId(id);
        contract.setState(1);
        //3.更新购销合同
        contractService.update(contract);//由于我们用的updatePrimaryKeySelective，所以只会更新有值的字段，其他字段根本不更新
        //4.重定向到列表页面
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 取消
     * 把购销合同的状态改为0
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id){
        //1.创建一个购销合同的对象
        Contract contract = new Contract();
        //2.设置购销合同的id和状态
        contract.setId(id);
        contract.setState(0);
        //3.更新购销合同
        contractService.update(contract);//由于我们用的updatePrimaryKeySelective，所以只会更新有值的字段，其他字段根本不更新
        //4.重定向到列表页面
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id){
        //1.根据id查询购销合同
        Contract contract = contractService.findById(id);
        //2.存入请求域中
        request.setAttribute("contract",contract);
        //3.前往编辑页面
        return "cargo/contract/contract-view";
    }

    /**
     * 异步验证是否允许提交和取消
     * @param id
     * @return
     */
    @RequestMapping("/checkUpdateState")
    public @ResponseBody String checkUpdateState(String id){
        Contract contract = contractService.findById(id);
        if(contract.getState() > 1){//2  3  4  5   6
            return "0";//不允许在提交和取消的返回值
        }
        return "1";//可以提交或者取消
    }
}
