package com.app.xxnr.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.PotentialDetailResult;
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.contract.PotentialDetailContract;
import com.app.xxnr.presenter.PotentialDetailPresenter;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.utils.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类描述：潜在客户详情
 * 作者：何鹏 on 2016/5/4 16:38
 * 邮箱：hepeng@xinxinnongren.com
 */
public class PotentialDetailActivity extends BaseSwipeBackActivity implements PotentialDetailContract.View {


    @BindView(R.id.customer_detail_phone)
    TextView customerDetailPhone;
    @BindView(R.id.customer_detail_name)
    TextView customerDetailName;
    @BindView(R.id.customer_detail_sex)
    TextView customerDetailSex;
    @BindView(R.id.customer_detail_address)
    TextView customerDetailAddress;
    @BindView(R.id.customer_intention_goods)
    TextView customerIntentionGoods;
    @BindView(R.id.customer_detail_remark)
    TextView customerDetailRemark;
    @BindView(R.id.customer_invite_name)
    TextView customerInviteName;
    @BindView(R.id.customer_detail_addTime)
    TextView customerDetailAddTime;
    @BindView(R.id.customer_detail_register_time)
    TextView customerDetailRegisterTime;


    @Inject
    PotentialDetailPresenter mPresenter;

    public static void startActivity(Context mContext, PotentialListResult.PotentialCustomersBean potential) {
        Intent intent = new Intent(mContext, PotentialDetailActivity.class);
        intent.putExtra("potential", potential);
        mContext.startActivity(intent);
    }


    @Override
    public int initContentView() {
        return R.layout.activity_potential_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        setTitle("潜在客户详情");
        ButterKnife.bind(this);
        mPresenter.attachView(this);
    }

    @Override
    public void initData() {
        PotentialListResult.PotentialCustomersBean potential = (PotentialListResult.PotentialCustomersBean) getIntent().getSerializableExtra("potential");
        //用户登记时间
        if (StringUtil.checkStr(potential.dateTimeAdded)) {
            customerDetailAddTime.setText(DateFormatUtils.convertTime(potential.dateTimeAdded));
        }
        //用户注册时间
        if (StringUtil.checkStr(potential.dateTimeRegistered)) {
            customerDetailRegisterTime.setText(DateFormatUtils.convertTime(potential.dateTimeRegistered));
        }
        mPresenter.load(potential._id);
    }


    @Override
    public void loadSuccess(PotentialDetailResult potentialDetailResult) {
        PotentialDetailResult.PotentialCustomerBean potentialCustomer = potentialDetailResult.potentialCustomer;
        if (potentialCustomer != null) {
            customerDetailName.setText(potentialCustomer.name);
            customerDetailPhone.setText(potentialCustomer.phone);
            if (potentialCustomer.sex) {
                customerDetailSex.setText("女");
            } else {
                customerDetailSex.setText("男");
            }
            //地址
            if (potentialCustomer.address != null) {

                String province = "";
                String city = "";
                String county = "";
                String town = "";
                if (potentialCustomer.address.province != null) {
                    province = potentialCustomer.address.province.name;
                }
                if (potentialCustomer.address.city != null) {
                    city = potentialCustomer.address.city.name;
                }
                if (potentialCustomer.address.county != null) {
                    county = potentialCustomer.address.county.name;
                }
                if (potentialCustomer.address.town != null) {
                    town = potentialCustomer.address.town.name;
                }
                String address = StringUtil.checkBufferStr
                        (province, city, county, town);
                customerDetailAddress.setText(address);
            }
            //意向商品
            if (potentialCustomer.buyIntentions != null && !potentialCustomer.buyIntentions.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < potentialCustomer.buyIntentions.size(); i++) {
                    PotentialDetailResult.PotentialCustomerBean.BuyIntentionsBean bean = potentialCustomer.buyIntentions.get(i);
                    if (bean != null) {
                        builder.append(bean.name);
                        if (i != potentialCustomer.buyIntentions.size() - 1) {
                            builder.append("，");
                        }
                    }
                }
                customerIntentionGoods.setText(builder.toString());
            } else {
                customerIntentionGoods.setText("");
            }
            //邀请人姓名
            if (potentialCustomer.user != null) {
                customerInviteName.setText(potentialCustomer.user.name);
            }

            //备注
            if (StringUtil.checkStr(potentialCustomer.remarks)) {
                customerDetailRemark.setText(potentialCustomer.remarks);
            }
        }

    }

    @Override
    public void showLoading() {
        showProgress();
    }

    @Override
    public void hideLoading() {
        dismissProgress();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
