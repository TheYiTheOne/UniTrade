package cn.edu.hitsz.common;

public enum Permission {
    VIEW_DATA(1, "查看各种信息", "VIEW_DATA"),
    OPEN_ORDER(2, "开单", "OPEN_ORDER"),
    AUDIT(3, "审核", "AUDIT"),
    RECEIVE_PAYMENT(4, "收款", "RECEIVE_PAYMENT"),
    RETURN_GOODS(5, "退货", "RETURN_GOODS"),
    INVENTORY_IN(6, "进货入库", "INVENTORY_IN"),
    TRANSFER_STOCK(7, "转移库存", "TRANSFER_STOCK");

    private final int id;
    private final String name;
    private final String code;
    private final int bitMask;

    Permission(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.bitMask = 1 << (id - 1); // bit0 ~ bit31
    }

    public static String VIEW_DATA() {
        return Permission.VIEW_DATA.getCode();
    }

    public static String OPEN_ORDER() {
        return Permission.OPEN_ORDER.getCode();
    }

    public static String AUDIT() {
        return Permission.AUDIT.getCode();
    }

    public static String RECEIVE_PAYMENT() {
        return Permission.RECEIVE_PAYMENT.getCode();
    }

    public static String RETURN_GOODS() {
        return Permission.RETURN_GOODS.getCode();
    }

    public static String INVENTORY_IN() {
        return Permission.INVENTORY_IN.getCode();
    }

    public static String TRANSFER_STOCK() {
        return Permission.TRANSFER_STOCK.getCode();
    }



    public int getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public int getBitMask() { return bitMask; }

    public static Permission fromId(int id) {
        for (Permission p : values()) {
            if (p.id == id) return p;
        }
        throw new IllegalArgumentException("未知权限 ID: " + id);
    }

    public static Permission fromCode(String code) {
        for (Permission p : values()) {
            if (p.code.equals(code)) return p;
        }
        throw new IllegalArgumentException("未知权限码: " + code);
    }
}