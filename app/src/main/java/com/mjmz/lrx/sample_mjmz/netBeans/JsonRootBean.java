/**
  * Copyright 2017 bejson.com 
  */
package com.mjmz.lrx.sample_mjmz.netBeans;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2017-08-25 21:34:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private String status;
    private Model model;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setModel(Model model) {
         this.model = model;
     }
     public Model getModel() {
         return convertNull(this.model,Model.class);
//         return model;
     }

    public static class Goods_properties {

        private String goods_property_id;
        private String goods_id;
        private String goods_prop_template_id;
        private String goods_custom_property;
        private String goods_property_value;
        private String goods_property_sort_num;
        public void setGoods_property_id(String goods_property_id) {
            this.goods_property_id = goods_property_id;
        }
        public String getGoods_property_id() {
            return goods_property_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_prop_template_id(String goods_prop_template_id) {
            this.goods_prop_template_id = goods_prop_template_id;
        }
        public String getGoods_prop_template_id() {
            return goods_prop_template_id;
        }

        public void setGoods_custom_property(String goods_custom_property) {
            this.goods_custom_property = goods_custom_property;
        }
        public String getGoods_custom_property() {
            return goods_custom_property;
        }

        public void setGoods_property_value(String goods_property_value) {
            this.goods_property_value = goods_property_value;
        }
        public String getGoods_property_value() {
            return goods_property_value;
        }

        public void setGoods_property_sort_num(String goods_property_sort_num) {
            this.goods_property_sort_num = goods_property_sort_num;
        }
        public String getGoods_property_sort_num() {
            return goods_property_sort_num;
        }

    }

    public static class Model {
        private String partner_goods_id;
        private String browse_num;
        private String shopping_num;
        private String goods_id;
        private String goods_brand_id;
        private String goods_brand_name;
        private String goods_series;
        private String goods_type_id;
        private String goods_type_name;
        private String goods_name;
        private String goods_spec;
        private String goods_style;
        private String goods_style_name;
        private String goods_dimension;
        private String goods_reference_price;
        private String goods_reference_unit_name;
        private String is_goods_group;
        private String goods_visiable;
        private String goods_color;
        private String goods_create_time;
        private String goods_description;
        private String goods_active_type;
        private String model_id;
        private String model_code;
        private String model_url;
        private String goods_img1;
        private String goods_img2;
        private String goods_img3;
        private String goods_img4;
        private String goods_img5;
        private String goods_version;
        private String goods_barcode;
        private String goods_unique_code;
        private int goods_price_lowest;
        private int goods_price_highest;
        private List<Goods_properties> goods_properties;
        public void setPartner_goods_id(String partner_goods_id) {
            this.partner_goods_id = partner_goods_id;
        }
        public String getPartner_goods_id() {
            return partner_goods_id;
        }

        public void setBrowse_num(String browse_num) {
            this.browse_num = browse_num;
        }
        public String getBrowse_num() {
            return browse_num;
        }

        public void setShopping_num(String shopping_num) {
            this.shopping_num = shopping_num;
        }
        public String getShopping_num() {
            return shopping_num;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_brand_id(String goods_brand_id) {
            this.goods_brand_id = goods_brand_id;
        }
        public String getGoods_brand_id() {
            return goods_brand_id;
        }

        public void setGoods_brand_name(String goods_brand_name) {
            this.goods_brand_name = goods_brand_name;
        }
        public String getGoods_brand_name() {
            return goods_brand_name;
        }

        public void setGoods_series(String goods_series) {
            this.goods_series = goods_series;
        }
        public String getGoods_series() {
            return goods_series;
        }

        public void setGoods_type_id(String goods_type_id) {
            this.goods_type_id = goods_type_id;
        }
        public String getGoods_type_id() {
            return goods_type_id;
        }

        public void setGoods_type_name(String goods_type_name) {
            this.goods_type_name = goods_type_name;
        }
        public String getGoods_type_name() {
            return goods_type_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec;
        }
        public String getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_style(String goods_style) {
            this.goods_style = goods_style;
        }
        public String getGoods_style() {
            return goods_style;
        }

        public void setGoods_style_name(String goods_style_name) {
            this.goods_style_name = goods_style_name;
        }
        public String getGoods_style_name() {
            return goods_style_name;
        }

        public void setGoods_dimension(String goods_dimension) {
            this.goods_dimension = goods_dimension;
        }
        public String getGoods_dimension() {
            return goods_dimension;
        }

        public void setGoods_reference_price(String goods_reference_price) {
            this.goods_reference_price = goods_reference_price;
        }
        public String getGoods_reference_price() {
//            return goods_reference_price;
            return convertNull(goods_reference_price,String.class);
        }

        public void setGoods_reference_unit_name(String goods_reference_unit_name) {
            this.goods_reference_unit_name = goods_reference_unit_name;
        }
        public String getGoods_reference_unit_name() {
//            return convertNull(this.getGoods_reference_unit_name(),String.class);
            return this.goods_reference_unit_name;
        }

        public void setIs_goods_group(String is_goods_group) {
            this.is_goods_group = is_goods_group;
        }
        public String getIs_goods_group() {
            return is_goods_group;
        }

        public void setGoods_visiable(String goods_visiable) {
            this.goods_visiable = goods_visiable;
        }
        public String getGoods_visiable() {
            return goods_visiable;
        }

        public void setGoods_color(String goods_color) {
            this.goods_color = goods_color;
        }
        public String getGoods_color() {
            return goods_color;
        }

        public void setGoods_create_time(String goods_create_time) {
            this.goods_create_time = goods_create_time;
        }
        public String getGoods_create_time() {
            return goods_create_time;
        }

        public void setGoods_description(String goods_description) {
            this.goods_description = goods_description;
        }
        public String getGoods_description() {
            return goods_description;
        }

        public void setGoods_active_type(String goods_active_type) {
            this.goods_active_type = goods_active_type;
        }
        public String getGoods_active_type() {
            return goods_active_type;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }
        public String getModel_id() {
            return model_id;
        }

        public void setModel_code(String model_code) {
            this.model_code = model_code;
        }
        public String getModel_code() {
            return model_code;
        }

        public void setModel_url(String model_url) {
            this.model_url = model_url;
        }
        public String getModel_url() {
            return model_url;
        }

        public void setGoods_img1(String goods_img1) {
            this.goods_img1 = goods_img1;
        }
        public String getGoods_img1() {
            return goods_img1;
        }

        public void setGoods_img2(String goods_img2) {
            this.goods_img2 = goods_img2;
        }
        public String getGoods_img2() {
            return goods_img2;
        }

        public void setGoods_img3(String goods_img3) {
            this.goods_img3 = goods_img3;
        }
        public String getGoods_img3() {
            return goods_img3;
        }

        public void setGoods_img4(String goods_img4) {
            this.goods_img4 = goods_img4;
        }
        public String getGoods_img4() {
            return goods_img4;
        }

        public void setGoods_img5(String goods_img5) {
            this.goods_img5 = goods_img5;
        }
        public String getGoods_img5() {
            return goods_img5;
        }

        public void setGoods_version(String goods_version) {
            this.goods_version = goods_version;
        }
        public String getGoods_version() {
            return goods_version;
        }

        public void setGoods_barcode(String goods_barcode) {
            this.goods_barcode = goods_barcode;
        }
        public String getGoods_barcode() {
            return goods_barcode;
        }

        public void setGoods_unique_code(String goods_unique_code) {
            this.goods_unique_code = goods_unique_code;
        }
        public String getGoods_unique_code() {
            return goods_unique_code;
        }

        public void setGoods_price_lowest(int goods_price_lowest) {
            this.goods_price_lowest = goods_price_lowest;
        }
        public int getGoods_price_lowest() {
            return goods_price_lowest;
        }

        public void setGoods_price_highest(int goods_price_highest) {
            this.goods_price_highest = goods_price_highest;
        }
        public int getGoods_price_highest() {
            return goods_price_highest;
        }

        public void setGoods_properties(List<Goods_properties> goods_properties) {
            this.goods_properties = goods_properties;
        }
        public List<Goods_properties> getGoods_properties() {
//            return goods_properties;
            return convertNull(goods_properties, ArrayList.class);
        }
    }

    public static <T,K> K convertNull(T t,Class<K> cls) {
        if(t == null) {
            K obj=null;
            try {
                obj=cls.newInstance();
            } catch (Exception e) {
                obj=null;
                Log.e("yy",e.toString());
            }
            return obj;
        }else {
            return (K) t;
        }
    }
}