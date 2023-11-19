// Generated by view binder compiler. Do not edit!
package com.dmribeiro87.cupcakeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dmribeiro87.cupcakeapp.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentCartBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton btCheckout;

  @NonNull
  public final CardView cvBottom;

  @NonNull
  public final ImageView ivEmptyCart;

  @NonNull
  public final TextView labelPrice;

  @NonNull
  public final RecyclerView rvList;

  @NonNull
  public final TextView tvEmptyCart;

  @NonNull
  public final TextView tvPrice;

  private FragmentCartBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton btCheckout, @NonNull CardView cvBottom,
      @NonNull ImageView ivEmptyCart, @NonNull TextView labelPrice, @NonNull RecyclerView rvList,
      @NonNull TextView tvEmptyCart, @NonNull TextView tvPrice) {
    this.rootView = rootView;
    this.btCheckout = btCheckout;
    this.cvBottom = cvBottom;
    this.ivEmptyCart = ivEmptyCart;
    this.labelPrice = labelPrice;
    this.rvList = rvList;
    this.tvEmptyCart = tvEmptyCart;
    this.tvPrice = tvPrice;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_checkout;
      MaterialButton btCheckout = ViewBindings.findChildViewById(rootView, id);
      if (btCheckout == null) {
        break missingId;
      }

      id = R.id.cv_bottom;
      CardView cvBottom = ViewBindings.findChildViewById(rootView, id);
      if (cvBottom == null) {
        break missingId;
      }

      id = R.id.iv_empty_cart;
      ImageView ivEmptyCart = ViewBindings.findChildViewById(rootView, id);
      if (ivEmptyCart == null) {
        break missingId;
      }

      id = R.id.label_price;
      TextView labelPrice = ViewBindings.findChildViewById(rootView, id);
      if (labelPrice == null) {
        break missingId;
      }

      id = R.id.rvList;
      RecyclerView rvList = ViewBindings.findChildViewById(rootView, id);
      if (rvList == null) {
        break missingId;
      }

      id = R.id.tv_empty_cart;
      TextView tvEmptyCart = ViewBindings.findChildViewById(rootView, id);
      if (tvEmptyCart == null) {
        break missingId;
      }

      id = R.id.tv_price;
      TextView tvPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvPrice == null) {
        break missingId;
      }

      return new FragmentCartBinding((ConstraintLayout) rootView, btCheckout, cvBottom, ivEmptyCart,
          labelPrice, rvList, tvEmptyCart, tvPrice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
