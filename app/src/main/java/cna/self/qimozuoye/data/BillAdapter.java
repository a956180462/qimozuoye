package cna.self.qimozuoye.data;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cna.self.qimozuoye.R;
import cna.self.qimozuoye.data.UserDataBase.ComeIn;
import cna.self.qimozuoye.data.UserDataBase.Expenses;
import cna.self.qimozuoye.data.UserDataBase.UserDAO;

/**
 *模板类，做"收入总览"和"支出总览"两个Fragment中的ListView的适配器（Adapter）
 *根据 <T> 的不同，Adapter 将持有不同的数据列表List<T>
 **/
public class BillAdapter<T> extends BaseAdapter {


    private List<T> dataList;
    private final Context context;
    UserDAO dao = DataBaseHolder.getDb().userDAO();

    /**构造函数，dataList是数据列表，传入Context用于构建删除弹窗*/
    public BillAdapter(List<T> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    //Adapter必须重写的几个方法
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * ListView中每个项（Item）的构建
     * 使用了SimpleDateFormat（文本格式化），忽略其警告
     * setText中没有使用strings.xml的方式，忽略其警告
     * */
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.view_list_item, parent, false);

            //获取控件信息：年、月、日、收入或支出的钱数、备注信息
            holder = new ViewHolder();
            holder.textYear = convertView.findViewById(R.id.textView_year);
            holder.textMon = convertView.findViewById(R.id.textView_mon);
            holder.textDay = convertView.findViewById(R.id.textView_day);
            holder.textMoney = convertView.findViewById(R.id.textView_money);
            holder.textRemark = convertView.findViewById(R.id.textView_remark);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        T data = dataList.get(position);
        //构造输出2023年、7月、13日这样的格式化工具，用做日期输出
        DateFormat dfy = new SimpleDateFormat("y年");
        DateFormat dfm = new SimpleDateFormat("M月");
        DateFormat dfd = new SimpleDateFormat("d日");
        // 通过判断data种类，进行不同的构造
        if(data instanceof ComeIn){
            holder.textYear.setText(dfy.format(((ComeIn) data).getDate()));
            holder.textMon.setText(dfm.format(((ComeIn) data).getDate()));
            holder.textDay.setText(dfd.format(((ComeIn) data).getDate()));
            // 收入显示钱数，在最前面加‘+’ e.g. +665.72元
            holder.textMoney.setText("+" + ((ComeIn) data).getMoney() + "元");
            holder.textRemark.setText(((ComeIn) data).getRemark());
        }else if (data instanceof Expenses){
            holder.textYear.setText(dfy.format(((Expenses) data).getDate()));
            holder.textMon.setText(dfm.format(((Expenses) data).getDate()));
            holder.textDay.setText(dfd.format(((Expenses) data).getDate()));
            // 收入显示钱数，这个前面自带‘-’ e.g. -665.72元
            holder.textMoney.setText(((Expenses) data).getMoney() + "元");
            holder.textRemark.setText(((Expenses) data).getRemark());
        }


        // 设置长按监听器，长按出现删除弹窗
        convertView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("删除确认")
                    .setMessage("确定要删除该项吗？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        Object temp = dataList.get(position);
                        // 执行删除操作，根据不同类的数据，从数据库中不同表中删除
                        // 由于只对一行操作，故在主线程中操作数据库
                        if (temp instanceof ComeIn ){
                            dao.delComeIn((ComeIn) temp);
                        }
                        if (temp instanceof Expenses ){
                            dao.delExpense((Expenses) temp);
                        }
                        // 从显示列表中删除
                        dataList.remove(position);
                        // 更新视图
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("取消", null)
                    .show();

            return true;
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView textYear;
        TextView textMon;
        TextView textDay;
        TextView textMoney;
        TextView textRemark;
    }

    /**
     * 变更需要显示的数据列表
     * */
    public void ChangeList(List<T> list){
        dataList = list;
    }
}