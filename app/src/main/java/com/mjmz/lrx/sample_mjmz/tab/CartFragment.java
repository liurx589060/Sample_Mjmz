package com.mjmz.lrx.sample_mjmz.tab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.example.imagewrapper.ImageWrapper;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.common.Const;
import com.mjmz.lrx.sample_mjmz.common.Datas;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.mjmz.lrx.sample_mjmz.map.MarkerStatus;
import com.mjmz.lrx.sample_mjmz.tools.SensorEventHelper;

import java.util.ArrayList;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class CartFragment extends BaseFragment implements AMapLocationListener,LocationSource,CloudSearch.OnCloudSearchListener
,AMap.OnMarkerClickListener,AMap.OnMarkerDragListener,AMap.OnInfoWindowClickListener,AMap.OnMapLoadedListener,AMap.InfoWindowAdapter{
    //控件类
    private MapView mMapView;
    private RecyclerView mRecyclerView;
    private TextView mLocationTextView;
    private MyRecyclerViewAdapter mAdapter;

    //高德地图
    private AMap aMap;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient = null;
    private String mCurrentCity;//默认城市
    private LatLonPoint mCenterPoint = new LatLonPoint(22.5492820000,113.9432170000);
    private SensorEventHelper mSensorHelper;
    private boolean mFirstFix = false;
    private Circle mCircle;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);//地位精度圈颜色
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);//地位精度圈颜色
    private Marker mLocMarker;
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    private ArrayList<CloudItem> mCloudItems;//云端的设置的item
    private CloudSearch mCloudSearch;//云端查询
    private CloudSearch.Query mQuery;
    private MarkerStatus mLastMarkerStatus;

    //数据类

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cart,null);

        //初始化
        init(rootView,savedInstanceState);

        return rootView;
    }

    /**
     * 初始化
     * @param view
     */
    public void init(View view,Bundle savedInstanceState) {
        //创建数据
        mCloudItems = new ArrayList<>();

        //找寻控件
        mMapView = (MapView) view.findViewById(R.id.cart_mapView);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recyclerView);
        mLocationTextView = (TextView) view.findViewById(R.id.cart_address);

        //设置数据和监听
        mMapView.onCreate(savedInstanceState);// 必须要写
        initMapView();

        // 注册云图搜索监听
        mCloudSearch = new CloudSearch(getContext());
        mCloudSearch.setOnCloudSearchListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new MyRecyclerViewAdapter(Datas.getImagesUrlArray());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 为地图增加一些事件监听
     */
    private void setMapListener() {
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
    }

    /**
     * 初始化AMap对象
     */
    private void initMapView() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        mSensorHelper = new SensorEventHelper(getContext());
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }

        //设置监听
        setMapListener();
        //定位
//        location();
    }

    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    /**
     * 根据选择的城市和行政区进行搜索
     *
     */
    private void searchByLocal() {
        CloudSearch.SearchBound bound = new CloudSearch.SearchBound(mCurrentCity);
        try {
            mQuery = new CloudSearch.Query(Const.mTableID, "", bound);
            mQuery.setPageSize(50);
            mQuery.setPageNum(0);
            mCloudSearch.searchCloudAsyn(mQuery);
            Log.e("yy","查询");
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        deactivate();
        mFirstFix = false;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 添加地位精度圈
     * @param latlng
     * @param radius
     */
    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    /**
     * 添加marker点
     * @param latlng
     */
    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
    }

    /**
     * 高德地图定位
     * @param location
     */
    @Override
    public void onLocationChanged(AMapLocation location) {
        stopLocation();
        if (location == null) {
            // 如果没有地理位置数据返回，则进行默认的搜索
//            searchDefault();
            return;
        }

        if (location.getErrorCode()!= AMapLocation.LOCATION_SUCCESS) {
            ToastUtil.setToast(getContext(),"定位失败");
            Log.e("yy","定位失败--" + location.getErrorCode() + ">>" + location.getErrorInfo());
            mLocationTextView.setText("定位失败--" + location.getErrorCode() + ">>" + location.getErrorInfo());
            return;
        }else{
            mLocationTextView.setText(location.getCity() + location.getAddress());
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            if (!mFirstFix) {
                mFirstFix = true;
                addCircle(loc, location.getAccuracy());//添加定位精度圆
                addMarker(loc);//添加定位图标
                mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
            } else {
                mCircle.setCenter(loc);
                mCircle.setRadius(location.getAccuracy());
                mLocMarker.setPosition(loc);
            }
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 18));
            searchDefault();
        }
    }

    /**
     * 进行默认的搜索 类型为根据城市行政区的搜索 默认的城市可以自己配置
     *
     */
    private void searchDefault() {
        mCurrentCity = "深圳";
        searchByLocal();
    }

    /**
     * marker被点击后的事件处理 改变他的当前状态 同时改变最后一个被点击的marker引用 以做到之前选中的现在是未选中的状态
     * 现在选中的呈现选中状态
     */
    @Override
    public boolean onMarkerClick(Marker arg0) {

        if (mLastMarkerStatus != null) {
            mLastMarkerStatus.pressStatusToggle();
            setMarkerBasedonStatus(mLastMarkerStatus);
        }

        MarkerStatus newMarkerStatus = (MarkerStatus) arg0.getObject();
        markerChosen(newMarkerStatus);
        mLastMarkerStatus = newMarkerStatus;
        return false;
    }

    /**
     * 往地图上添加marker，为列表页获得的数据
     */
    private void addMarkersToMap() {

        int size = mCloudItems.size();
        for (int i = 0; i < size; i++) {

            // 根据该poi的经纬度进行marker点的添加
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(mCloudItems.get(i)
                    .getLatLonPoint().getLatitude(), mCloudItems.get(i)
                    .getLatLonPoint().getLongitude()));
            Marker marker = aMap.addMarker(markerOption);

            // 每个marker点上带有一个状态类，来说明这个marker是否是被选中的状态
            // 会根据是否被选中来决定一些事件处理
            MarkerStatus markerStatus = new MarkerStatus(i);
            markerStatus.setCloudItem(mCloudItems.get(i));
            markerStatus.setMarker(marker);
            if (i == 0) {
                markerChosen(markerStatus);
                mLastMarkerStatus = markerStatus;
            }
            setMarkerBasedonStatus(markerStatus);
            marker.setObject(markerStatus);
        }

        moveMapToSeeAllMarker();

    }

    /**
     * 根据该marker的最新状态决定应该显示什么样的marker
     *
     * @param status
     */
    private void setMarkerBasedonStatus(MarkerStatus status) {
        if (status.getIsPressed()) {
            status.getMarker().setIcon(
                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    status.getmResPressed())));
        } else {
            status.getMarker().setIcon(
                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    status.getmResUnPressed())));
        }
    }

    /**
     * marker被选中之后，需要更改marker的样式，以及在底部bar显示信息
     *
     * @param markerStatus
     */
    private void markerChosen(MarkerStatus markerStatus) {
        markerStatus.pressStatusToggle();
        setMarkerBasedonStatus(markerStatus);
    }

    /**
     * 销毁定位
     */
    private void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.unRegisterLocationListener(this);
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * moveMap能够看到所有的Marker
     */
    private void moveMapToSeeAllMarker() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mCloudItems.size() == 0) {
            return;
        }
        if (mCloudItems.size() == 1) {

            aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
                    mCloudItems.get(0).getLatLonPoint().getLatitude(),
                    mCloudItems.get(0).getLatLonPoint().getLongitude())));
            return;
        }

        // 当数据大于等于2的时候，才谈得上是一个bound
        for (CloudItem item : mCloudItems) {
            builder.include(new LatLng(item.getLatLonPoint().getLatitude(),
                    item.getLatLonPoint().getLongitude()));
        }

        LatLngBounds bounds;
        bounds = builder.build();

        // 设置所有maker显示在当前可视区域地图中
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
    }

    /**
     * 激活地位
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onCloudSearched(CloudResult cloudResult, int i) {
        Log.e("yy","查询结果=" + cloudResult.getClouds().size());
        if(cloudResult != null) {
            mCloudItems.clear();
            mCloudItems.addAll(cloudResult.getClouds());
            addMarkersToMap();
        }
    }

    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * Adapter
     */
    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private ArrayList<String> adapterList;

        public MyRecyclerViewAdapter(ArrayList<String> list) {
            this.adapterList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerview_item,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder)holder).bindVH(position);
        }

        @Override
        public int getItemCount() {
            return adapterList.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private TextView mTitle;
            private TextView mSubTitle;

            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.cart_recyclerView_item_image);
                mTitle = (TextView) itemView.findViewById(R.id.cart_recyclerView_item_title);
                mSubTitle = (TextView) itemView.findViewById(R.id.cart_recyclerView_item_subTitle);

            }

            public void bindVH(int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position)).setImageView(mImageView);
                mTitle.setText("商品名称-" + position);
                mSubTitle.setText("副标题-" + position);
            }
        }
    }
}
