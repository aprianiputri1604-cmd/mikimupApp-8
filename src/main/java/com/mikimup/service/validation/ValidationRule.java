package com.mikimup.service.validation;

/** OCP: aturan baru = kelas baru yang implement interface ini, tanpa mengubah ProductService. */
public interface ValidationRule<T> {
    /** @return pesan kesalahan, atau null bila valid */
    String validate(T target);
}
