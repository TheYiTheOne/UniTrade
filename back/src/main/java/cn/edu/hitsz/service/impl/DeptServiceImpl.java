package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.mapper.DeptMapper;
import cn.edu.hitsz.mapper.EmpMapper;
import cn.edu.hitsz.pojo.Dept;
import cn.edu.hitsz.pojo.DeptLog;
import cn.edu.hitsz.service.DeptLogService;
import cn.edu.hitsz.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private DeptLogService deptLogService;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    //@Transactional(rollbackFor = Exception.class) //spring事务管理
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
        try {
            deptMapper.deleteById(id); //根据ID删除部门数据

            //int i = 1/0;
            //if(true){throw new Exception("出错啦...");}

            empMapper.deleteByDeptId(id); //根据部门ID删除该部门下的员工
        } finally {
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作,此次解散的是"+id+"号部门");
            deptLogService.insert(deptLog);
        }
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }
}
