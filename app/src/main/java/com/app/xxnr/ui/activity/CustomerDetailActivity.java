package com.app.xxnr.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.xxnr.R;
import com.app.xxnr.bean.CustomerDetailResult;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.bean.RscInfoResult;
import com.app.xxnr.contract.CustomerDetailContract;
import com.app.xxnr.event.UpdateCustomer;
import com.app.xxnr.ui.BaseSwipeBackActivity;
import com.app.xxnr.presenter.CustomerDetailPresenter;
import com.app.xxnr.utils.xxnr.DateFormatUtils;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.widget.CustomDialog;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类描述：客户详情
 * 作者：何鹏 on 2016/8/18 11:25
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CustomerDetailActivity extends BaseSwipeBackActivity implements CustomerDetailContract.View {

    @BindView(R.id.customer_detail_phone)
    TextView customerDetailPhone;
    @BindView(R.id.customer_detail_name)
    TextView customerDetailName;
    @BindView(R.id.customer_detail_sex)
    TextView customerDetailSex;
    @BindView(R.id.customer_detail_address)
    TextView customerDetailAddress;
    @BindView(R.id.customer_detail_score)
    TextView customerDetailScore;
    @BindView(R.id.customer_detail_invitee_name)
    TextView customerDetailInviteeName;
    @BindView(R.id.customer_detail_create_time)
    TextView customerDetailCreateTime;
    @BindView(R.id.customer_detail_applyType)
    TextView customerDetailApplyType;
    @BindView(R.id.customer_approve_icon)
    TextView customerApproveIcon;
    @BindView(R.id.approve_customer)
    TextView approveCustomer;
    @BindView(R.id.connty_approve_icon)
    TextView conntyApproveIcon;
    @BindView(R.id.is_fill_county_type)
    TextView isFillCountyType;
    @BindView(R.id.approve_county_customer)
    TextView approveCountyCustomer;
    @BindView(R.id.rsc_name)
    TextView rscName;
    @BindView(R.id.rsc_card_id)
    TextView rscCardId;
    @BindView(R.id.rsc_company_name)
    TextView rscCompanyName;
    @BindView(R.id.rsc_company_phone)
    TextView rscCompanyPhone;
    @BindView(R.id.rsc_rsc_company_address)
    TextView rscRscCompanyAddress;
    @BindView(R.id.rsc_info_ll)
    LinearLayout rscInfoLl;

    @Inject
    CustomerDetailPresenter mPresenter;

    CustomerListResult.Users.ItemsBean customer;


    public static void startActivity(Context mContext, CustomerListResult.Users.ItemsBean customer) {
        Intent intent = new Intent(mContext, CustomerDetailActivity.class);
        intent.putExtra("customer", customer);
        mContext.startActivity(intent);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_customer_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        setTitle("客户详情");

        rscInfoLl.setVisibility(View.GONE);
        conntyApproveIcon.setVisibility(View.GONE);
        customerApproveIcon.setVisibility(View.GONE);

    }

    @Override
    public void initData() {
        customer = (CustomerListResult.Users.ItemsBean) getIntent().getSerializableExtra("customer");
        if (customer != null) {
            mPresenter.onLoadDetail(customer.id);
            mPresenter.onLoadRscInfo(customer._id);
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessDetail(CustomerDetailResult detailResult) {
        final CustomerDetailResult.UserBean user = detailResult.user;
        if (user != null) {
            customerDetailName.setText(user.name);
            customerDetailPhone.setText(user.account);
            if (user.sex) {
                customerDetailSex.setText("女");
            } else {
                customerDetailSex.setText("男");
            }
            //地址
            if (user.address != null) {

                String province = "";
                String city = "";
                String county = "";
                String town = "";
                if (user.address.province != null) {
                    province = user.address.province.name;
                }
                if (user.address.city != null) {
                    city = user.address.city.name;
                }
                if (user.address.county != null) {
                    county = user.address.county.name;
                }
                if (user.address.town != null) {
                    town = user.address.town.name;
                }
                String address = StringUtil.checkBufferStr
                        (province, city, county, town);
                customerDetailAddress.setText(address);
            }
            //积分
            if (user.score == 0) {
                customerDetailScore.setText("");

            } else {
                customerDetailScore.setText(user.score + "");
            }
            CustomerDetailResult.UserBean.InviterBean inviter = user.inviter;
            //邀请人姓名
            if (inviter != null) {
                customerDetailInviteeName.setText(inviter.name);
            }
            //用户注册时间
            customerDetailCreateTime.setText(DateFormatUtils.convertTime(user.datecreated));
            //申请认证类型
            switch (user.type) {
                case "1":
                    customerDetailApplyType.setText("普通用户");
                    break;
                case "5":
                    customerDetailApplyType.setText("县级经销商");
                    break;
                case "6":
                    customerDetailApplyType.setText("新农经纪人");
                    break;
                default:
                    customerDetailApplyType.setText("");
                    break;
            }
            //是否认证
            approveCountyCustomer.setVisibility(View.VISIBLE);
            approveCustomer.setVisibility(View.VISIBLE);

            if (user.isRSC) {
                approveCountyCustomer.setText("取消认证");
                approveCountyCustomer.setBackgroundResource(R.drawable.gray_corners_bg);
                approveCountyCustomer.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    if (user.isXXNRAgent) {
                        list.add("6");
                    }
                    approveOrCancel(user.id, list, "确定取消认证该客户吗？");
                });
                conntyApproveIcon.setVisibility(View.VISIBLE);
            } else {
                approveCountyCustomer.setText("认证客户");
                Drawable drawable = getResources().getDrawable(R.drawable.selector_green_lightgreen);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    approveCountyCustomer.setBackground(drawable);
                } else {
                    approveCountyCustomer.setBackgroundResource(R.drawable.green_corners_bg);
                }
                conntyApproveIcon.setVisibility(View.GONE);
                approveCountyCustomer.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    if (user.isXXNRAgent) {
                        list.add("6");
                    }
                    list.add("5");
                    approveOrCancel(user.id, list, "确定认证该客户吗？");
                });
            }
            if (user.isXXNRAgent) {
                approveCustomer.setText("取消认证");
                approveCustomer.setBackgroundResource(R.drawable.gray_corners_bg);
                customerApproveIcon.setVisibility(View.VISIBLE);
                approveCustomer.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    if (user.isRSC) {
                        list.add("5");
                    }
                    approveOrCancel(user.id, list, "确定取消认证该客户吗？");
                });
            } else {
                approveCustomer.setText("认证客户");
                Drawable drawable = getResources().getDrawable(R.drawable.selector_green_lightgreen);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    approveCustomer.setBackground(drawable);
                } else {
                    approveCustomer.setBackgroundResource(R.drawable.green_corners_bg);
                }
                customerApproveIcon.setVisibility(View.GONE);
                approveCustomer.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    if (user.isRSC) {
                        list.add("5");
                    }
                    list.add("6");
                    approveOrCancel(user.id, list, "确定认证该客户吗？");
                });
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessRscInfo(RscInfoResult result) {
        if (result.RSCInfo != null && StringUtil.checkStr(result.RSCInfo.companyName)) {
            rscInfoLl.setVisibility(View.VISIBLE);
            isFillCountyType.setText("");
            rscCardId.setText(result.RSCInfo.IDNo);
            rscCompanyName.setText(result.RSCInfo.companyName);
            rscName.setText(result.RSCInfo.name);
            rscCompanyPhone.setText(result.account);
            String province = "";
            String city = "";
            String county = "";
            String town = "";
            RscInfoResult.RSCInfoBean.CompanyAddressBean companyAddress = result.RSCInfo.companyAddress;
            if (companyAddress != null) {
                if (companyAddress.province != null) {
                    province = companyAddress.province.name;
                }
                if (companyAddress.city != null) {
                    city = companyAddress.city.name;
                }
                if (companyAddress.county != null) {
                    county = companyAddress.county.name;
                }
                if (companyAddress.town != null) {
                    town = companyAddress.town.name;
                }
                String address = StringUtil.checkBufferStr
                        (province, city, county, town);

                if (StringUtil.checkStr(companyAddress.details)) {
                    rscRscCompanyAddress.setText(address + companyAddress.details);
                }
            }
        } else {
            rscInfoLl.setVisibility(View.GONE);
            isFillCountyType.setText("未填写认证信息");
        }
    }


    @Override
    public void onSuccessChange() {
        EventBus.getDefault().post(new UpdateCustomer());
        if (customer != null) {
            mPresenter.onLoadDetail(customer.id);
        }
    }


    //认证或者取消认证
    private void approveOrCancel(final String id, final List<String> list, String msg) {

        CustomDialog.Builder builder = new CustomDialog.Builder(
                CustomerDetailActivity.this);
        builder.setMessage(msg)
                .setPositiveButton("确定",
                        (dialog, which) -> {
                            dialog.dismiss();
                            mPresenter.changeCustomer(id, list);
                        })
                .setNegativeButton("取消",
                        (dialog, which) -> {
                            dialog.dismiss();
                        });
        builder.create().show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
