/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 * <p/>
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 * <p/>
 * AndTinder is compatible with API Level 13 and upwards
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.andtinder.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class CardModel {

    private String nombreUsuario;
    private String apellidoUsuario;
    private String idUsuario;
    private String edadUsuario;


    private String title;
    private String description;
    private Drawable cardImageDrawable;
    private Drawable cardLikeImageDrawable;
    private Drawable cardDislikeImageDrawable;

    private OnCardDimissedListener mOnCardDimissedListener = null;

    private OnClickListener mOnClickListener = null;

    public interface OnCardDimissedListener {
        void onLike();

        void onDislike();
    }

    public interface OnClickListener {
        void OnClickListener();
    }

    public CardModel() {
        this(null, null, (Drawable) null);
    }

    public CardModel(String title, String description, Drawable cardImage) {
        this.title = title;
        this.description = description;
        this.cardImageDrawable = cardImage;
    }

    public CardModel(String title, String description, Bitmap cardImage) {
        this.title = title;
        this.description = description;
        this.cardImageDrawable = new BitmapDrawable(null, cardImage);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getCardImageDrawable() {
        return cardImageDrawable;
    }

    public void setCardImageDrawable(Drawable cardImageDrawable) {
        this.cardImageDrawable = cardImageDrawable;
    }

    public Drawable getCardLikeImageDrawable() {
        return cardLikeImageDrawable;
    }

    public void setCardLikeImageDrawable(Drawable cardLikeImageDrawable) {
        this.cardLikeImageDrawable = cardLikeImageDrawable;
    }

    public Drawable getCardDislikeImageDrawable() {
        return cardDislikeImageDrawable;
    }

    public void setCardDislikeImageDrawable(Drawable cardDislikeImageDrawable) {
        this.cardDislikeImageDrawable = cardDislikeImageDrawable;
    }

    public void setOnCardDimissedListener(OnCardDimissedListener listener) {
        this.mOnCardDimissedListener = listener;
    }

    public OnCardDimissedListener getOnCardDimissedListener() {
        return this.mOnCardDimissedListener;
    }


    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return this.mOnClickListener;
    }

    //TODO: Borrar si no se usan

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public void setEdadUsuario(String edadUsuario) {
        this.edadUsuario = edadUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public String getEdadUsuario() {
        return edadUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }
}