package com.doan.cnpm.context;

public class ApplicationContextHolder {

    private static final ThreadLocal<TutorContext> TUTOR_CONTEXT_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static TutorContext getWalletContext() {
        return TUTOR_CONTEXT_THREAD_LOCAL.get();
    }

    public static void setWalletContext(TutorContext tutorContext) {
        TUTOR_CONTEXT_THREAD_LOCAL.set(tutorContext);
    }

}
