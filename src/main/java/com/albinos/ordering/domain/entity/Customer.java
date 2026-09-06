package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.CustomerArchivedException;
import com.albinos.ordering.domain.valueobject.*;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;
    private Address address;

    @Builder(builderClassName = "BrandNewCustomerBuild", builderMethodName = "brandNew")
    private static Customer newBrandNewCustomer(CustomerId id, Email email, BirthDate birthDate,
                                    FullName fullName, Document document,
                                    Phone phone, Boolean promotionNotificationsAllowed,
                                    Address address){
        return new Customer(id,
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltyPoints.ZERO,
                address
        );

    }
    @Builder(builderClassName = "BrandExistingCustomerBuild", builderMethodName = "existing")
    private static Customer newExistingCustomer(CustomerId id, FullName fullName, BirthDate birthDate, Email email,
                                    Phone phone, Document document, Boolean promotionNotificationsAllowed,
                                    Boolean archived, OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                                    LoyaltyPoints loyaltyPoints, Address address){

        return new Customer(
                id,
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                archived,
                registeredAt,
                archivedAt,
                loyaltyPoints,
                address
        );

    }

    private Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email,
                    Phone phone, Document document, Boolean promotionNotificationsAllowed,
                    Boolean archived, OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                    LoyaltyPoints loyaltyPoints, Address address) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
        this.setAddress(address);
    }

    public void addLoayltyPoints(Integer loyaltyPointsAdded){
        verifyIfChangeable();
        if (loyaltyPointsAdded <= 0){
            throw new IllegalArgumentException();
        }
        this.setLoyaltyPoints(this.loyaltyPoints().add(loyaltyPointsAdded));
    }

    public void archive(){
        verifyIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationsAllowed(false);
        Address.AddressBuilder addressBuilder = this.address.toBuilder();
        this.setAddress(addressBuilder.number("Anonymized").complement(null).build());

    }

    private void verifyIfChangeable() {
        if(this.isArchived()){
            throw new CustomerArchivedException();
        }
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    public Address address() {
        return address;
    }

    public void enablePromotionNotifications(){
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(true);
    }

    public void disablePromotionNotications(){
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(FullName fullName){
        verifyIfChangeable();
        this.setFullName(fullName);
    }

    public void changeEmail(Email email){
        verifyIfChangeable();
        this.setEmail(email);
    }

    public void changePhone(Phone phone){
        verifyIfChangeable();
        this.setPhone(phone);
    }

    public void changeAddress(Address address){
        verifyIfChangeable();
        this.setAddress(address);
    }

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName);
        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {
        Objects.requireNonNull(email);
        this.email = email;
    }

    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setDocument(Document document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }

    private void setAddress(Address address) {
        Objects.requireNonNull(address);
        this.address = address;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
