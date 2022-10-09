package io.github.bytecode.bytecodediscordbot.utils;

public enum ID {
    GUILD_ID("1023978813895479316"),
    VERIFY_CHANNEL("1023985525998223420"),
    MEMBER_ROLE("1023978813895479318");
    private String id;

    ID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
