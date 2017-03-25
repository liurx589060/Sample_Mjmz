package jp.wasabeef.recyclerview.adapters;

import android.animation.Animator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import jp.wasabeef.recyclerview.internal.ViewHelper;

/**
 * Copyright (C) 2017 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class AnimationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private int mDuration = 300;
  private Interpolator mInterpolator = new LinearInterpolator();
  private int mLastPosition = -1;

  private boolean isFirstOnly = true;
  private RecyclerView mRecyclerView;


  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
    super.registerAdapterDataObserver(observer);
  }

  @Override public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
    super.unregisterAdapterDataObserver(observer);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//    int adapterPosition = holder.getAdapterPosition();
//    int lastItemPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
//    boolean isAnimate = false;
//    Log.e("yy","adapter=" + adapterPosition + "-->>last=" + lastItemPosition);
//    if(!isFirstOnly || adapterPosition > lastItemPosition) {
//      isAnimate = true;
//    }else {
//      isAnimate =false;
//    }
//
//    if (isAnimate) {
//      for (Animator anim : getAnimators(holder.itemView)) {
//        anim.setDuration(mDuration).start();
//        anim.setInterpolator(mInterpolator);
//      }
//      mLastPosition = adapterPosition;
//    } else {
//      ViewHelper.clear(holder.itemView);
//    }

    int adapterPosition = holder.getAdapterPosition();
    if (!isFirstOnly || adapterPosition > mLastPosition) {
      for (Animator anim : getAnimators(holder.itemView)) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
      }
      mLastPosition = adapterPosition;
    } else {
      ViewHelper.clear(holder.itemView);
    }
  }

  @Override public void onViewRecycled(RecyclerView.ViewHolder holder) {
    super.onViewRecycled(holder);
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    this.mRecyclerView = recyclerView;
  }

  @Override public int getItemCount() {
    return 0;
  }

  public void setDuration(int duration) {
    mDuration = duration;
  }

  public void setInterpolator(Interpolator interpolator) {
    mInterpolator = interpolator;
  }

  public void setStartPosition(int start) {
    mLastPosition = start;
  }

  protected abstract Animator[] getAnimators(View view);

  public void setFirstOnly(boolean firstOnly) {
    isFirstOnly = firstOnly;
  }

  @Override public int getItemViewType(int position) {
    return 0;
  }
}
