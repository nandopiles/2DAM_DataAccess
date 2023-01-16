//create de db
use social-network
//create the collection
db.createCollection("user")

/*
    1 => During login users of our social network tell us their name, surname, age and gender.
        We also insert the registration date at the time of registration.
        As identifier we want to use the Long type (use NumberLong()), not the ObjectID that Mongo gives us by default.
        Let's say that two users are registered. Write the statements to insert them into the "user" collection
 */
db.user.insertMany([
{_id: NumberLong(5), name: "Juan", surname: "García Castellano", age: 23, gender: "male", insertDate: new Date()},
{_id: NumberLong(6), name: "Beatriz", surname: "Pérez Sola", age: 27, gender: "female", insertDate: new Date()}
])

/*
    2 => Write the command to retrieve all documents in the "user" collection.
 */
db.user.find()

/*
    3 => Users can belong to as many groups as they want,
        and we save those groups in an Array field called groups in each user.
        Insert the new user Jorge Lopez Sevilla, with identifier 7, who does not tell us his age,
        and who belongs to the groups "basketball", "kitchen" and "historical novel".
 */
db.user.insertOne(
{_id: NumberLong(7), name: "Jorge", surname: "López Sevilla", gender: "male", insertDate: new Date(), groups: ["basketball", "kitchen", "historical novel"]}
)

/*
    4 =>Juan García, with identifier 5 is unsubscribed. Write the sentence to delete it.
 */
db.user.deleteOne({_id: 5})

/*
    5 => The user Beatriz, with identifier 6, signs up for two groups: "historical novel" and "dance".
        Write the statement to update these fields in your document without re-reporting the rest.
        Remember that groups are saved as arrays.
 */
db.user.updateOne({_id: 6}, {$push: {groups: {$each: ["historical novel", "dance"]}}})

/*
    6 => In our social network you can also register companies, which we keep in the collection "company",
        also with Long identifier, and for which at the moment we only store the name of the company.
        Write the command to insert the company "Gardening Gardenia" with identifier 10.
 */
db.createCollection("company")
db.company.insertOne({_id: NumberLong(10), name: "Gardening Gardenia"})

/*
    7 => Now you must update the data of the company "Gardening Gardenia" adding the following fields:
 */
db.company.updateOne(
{_id: 10},
{$set: {address: {street: "Palmeras", number: 8, town: "Torrente"}}, sector: "services", web: "http://www.gardeninggardenia.com"}}
)

/*
    8 => We're going to count the followers of the companies of the social network, using a field "followers" in the collection "company".
        Five users have marked FOLLOW the company "Gardening Gardenia".
        Type the command to create the "followers" field being equal to 5.
        Then two people have followed the company. Type the command to increment it.
        Finally, one has unfollowed. Also type the command to decrease it.
 */
db.company.updateOne({_id: 10}, {$set: {followers: 5}})
db.company.updateOne({_id: 10}, {$inc: {followers: 2}})
db.company.updateOne({_id: 10}, {$inc: {followers: -1}})


/*
    9 => Update the address of the company "Gardening Gardenia", add the postal code 46009.
 */
db.company.updateOne({_id: 10}, {$set: {'address.postal': 46009}})

/*
    10 => Eliminate the field "sector" of the company "Gardening Gardenia", leaving intact the rest of the fields.
 */
db.company.updateOne({_id: 10}, {$unset: {sector: ''}})

/*
    11 => The user Beatriz, with identifier 6, signs up for the group "theater".
    Write the command to add that group to its array.
 */
db.user.updateOne({_id: 6}, {$push: {groups: "theater"}})

/*
    12 => The user with identifier 6 is deleted from the "dance" group.
    Write the statement to remove it from its group array.
 */
db.user.updateOne({_id: 6}, {$pull: {groups: 'dance'}})

/*
    13 => As in any social network, users can enter comments. In our case the comments have several fields, which are.
        Title
        Text
        The group to which the comment belongs.
        Date
        Comments will be saved in the "user" collection itself, in a new "comments" field, which will be an Array of objects with the previous properties.
        In addition, at the user level, we will also keep a counter of the number of total comments made by each user, in a field "total_comments", which we will increase each time we insert a new comment.
        Write the command to insert a new comment for the user Jorge Lopez Sevilla in the group "historical novel", while increasing the "total_comments" by one.
        Afterwards, write the command to insert a new comment for the user Jorge Lopez Sevilla in the group "basketball", while increasing the "total_comments" by one.
 */
db.user.updateOne({name: 'Jorge', surname: 'López Sevilla'}, {$set: {comments: [{
    title: 'Comment 1',
    text: 'Hola pixa, aquí Joaquín',
    group: 'historical novel',
    date: new Date}], total_comments: 1}})

db.user.updateOne({name: 'Jorge', surname: 'López Sevilla'}, {$push: {comments: [{
    title: 'Comment 2',
    text: 'La nueva Gilett',
    group: 'basketball',
    date: new Date}]}, $inc: {total_comments: 1}}})

/*
    14 => Write the commands to retrieve the following information:
        Name, surname, age and _id of those users that belong to “historical novel” group and are older than 25 years old.
        Name, surname and groups (but not the _id) of those users belonging to at least 2 groups.
        Name, surname and groups (but not the _id) of those users belonging to “historical novel” group and “theater”.
        Name, surname of those users that have a field named comments.
        Name of the companies that are in Torrente and have zero followers.
        Name of the companies that are in Torrente and have more than 5 followers.
 */
db.user.find({groups: 'historical novel', age: {$gte: 25}}, {_id: 1, name: 1, surname: 1, age: 1})

db.user.find({'groups.1': {$exists: true}}, {name: 1, surname: 1, groups: 1, _id: 0})

db.user.find({groups: {$all: ['historical novel', 'theater']}}, {name: 1, surname: 1, groups: 1, _id: 0})

db.user.find({comments: {$exists: true}}, {name: 1, surname: 1})

db.company.find({'address.town': 'Torrente', followers: 0}, {name: 1, _id: 0})

db.company.find({'address.town': 'Torrente', followers: {$gte: 5}}, {name: 1, _id: 0})


