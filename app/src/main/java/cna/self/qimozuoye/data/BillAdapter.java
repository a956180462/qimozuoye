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

public class BillAdapter<T> extends BaseAdapter {
    private List<T> dataList;
    private final Context context;
    UserDAO dao = DataBaseHolder.getDb().userDAO();

    public BillAdapter(List<T> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

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

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.view_list_item, parent, false);

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

        DateFormat dfy = new SimpleDateFormat("y年");
        DateFormat dfm = new SimpleDateFormat("M月");
        DateFormat dfd = new SimpleDateFormat("d日");
        if(data instanceof ComeIn){
            holder.textYear.setText(dfy.format(((ComeIn) data).getDate()));
            holder.textMon.setText(dfm.format(((ComeIn) data).getDate()));
            holder.textDay.setText(dfd.format(((ComeIn) data).getDate()));
            holder.textMoney.setText("+" + ((ComeIn) data).getMoney() + "元");
            holder.textRemark.setText(((ComeIn) data).getRemark());
        }else if (data instanceof Expenses){
            holder.textYear.setText(dfy.format(((Expenses) data).getDate()));
            holder.textMon.setText(dfm.format(((Expenses) data).getDate()));
            holder.textDay.setText(dfd.format(((Expenses) data).getDate()));
            holder.textMoney.setText(((Expenses) data).getMoney() + "元");
            holder.textRemark.setText(((Expenses) data).getRemark());
        }


        // 设置长按监听器
        convertView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("删除确认")
                    .setMessage("确定要删除该项吗？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        Object temp = dataList.get(position);
                        // 执行删除操作
                        if (temp instanceof ComeIn ){
                            dao.delComeIn((ComeIn) temp);
                        }
                        if (temp instanceof Expenses ){
                            dao.delExpense((Expenses) temp);
                        }
                        dataList.remove(position);
                        notifyDataSetChanged();
                        // TODO: 根据需要更新相关的数据或界面
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

    public void ChangeList(List<T> list){
        dataList = list;
    }
}