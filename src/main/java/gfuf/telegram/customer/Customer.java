package gfuf.telegram.customer;

import gfuf.telegram.bot.receive.State;

import java.util.Objects;

public class Customer
{
    private final Integer id;

    private final Integer telegramUserId;

    private final CustomerRole role;

    private final State state;

    private final Long privateChatId;

    public Customer(Builder builder)
    {
        this.id = builder.getId();
        this.telegramUserId = builder.getTelegramUserId();
        this.role = builder.getRole();
        this.state = builder.getState();
        this.privateChatId = builder.getPrivateChatId();
    }

    public Integer getId()
    {
        return id;
    }

    public Integer getTelegramUserId()
    {
        return telegramUserId;
    }

    public CustomerRole getRole()
    {
        return role;
    }

    public State getState()
    {
        return state;
    }

    public Long getPrivateChatId()
    {
        return privateChatId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(telegramUserId, customer.telegramUserId) &&
                role == customer.role &&
                state == customer.state &&
                Objects.equals(privateChatId, customer.privateChatId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, telegramUserId, role, state, privateChatId);
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", telegramUserId=" + telegramUserId +
                ", role=" + role +
                ", state=" + state +
                ", privateChatId='" + privateChatId + '\'' +
                '}';
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private Integer id;

        private Integer telegramUserId;

        private CustomerRole role;

        private State state;

        private Long privateChatId;

        public Builder from(Customer customer)
        {

            return setId(customer.getId())
                    .setTelegramUserId(customer.getTelegramUserId())
                    .setRole(customer.getRole())
                    .setState(customer.getState())
                    .setPrivateChatId(customer.getPrivateChatId());
        }

        public Integer getId()
        {
            return id;
        }

        public Builder setId(Integer id)
        {
            this.id = id;
            return this;
        }

        public Integer getTelegramUserId()
        {
            return telegramUserId;
        }

        public Builder setTelegramUserId(Integer telegramUserId)
        {
            this.telegramUserId = telegramUserId;
            return this;
        }

        public CustomerRole getRole()
        {
            return role;
        }

        public Builder setRole(CustomerRole role)
        {
            this.role = role;
            return this;
        }

        public State getState()
        {
            return state;
        }

        public Builder setState(State state)
        {
            this.state = state;
            return this;
        }

        public Long getPrivateChatId()
        {
            return privateChatId;
        }

        public Builder setPrivateChatId(Long privateChatId)
        {
            this.privateChatId = privateChatId;
            return this;
        }

        public Customer build()
        {
            return new Customer(this);
        }
    }

}
