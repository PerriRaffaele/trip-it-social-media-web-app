import {createStore} from 'vuex'
import router from "@/router";
import journeyElement from "@/components/JourneyElement.vue";
import {off} from "leaflet/src/dom/DomEvent";
export default createStore({
    state: {

        logged_in: Boolean = true,

        user: {

        },

        journey: {

        },

        userJourneys: [
        ],

        selectedUser: {

        },

        otherUsers: [
        ],

        places: [

        ],

        otherUserJourneys: [

        ],

        allOtherUserJourneys: [

        ],

        journeyActivities: [

        ],

        usersActivities: [

        ],
        // notification
        likes: [

        ],

        friends: [

        ],

        friendsJourney: [

        ],
        notificationActivity: [

            ],
        notificationJourney: [

            ],
        photoArray: [
            ],
        imageId: {},
        actualImageId: {},
        image: {}
    },

    getters: {

        getUserId(state) {
            return state.user.id
        },


        getUser(state) {
            return state.user
        },

        getUserJourneys(state) {
            return state.userJourneys
        },

        getOtherUserById: (state) => (userId) => {
            console.dir(state.otherUsers)
            return state.otherUsers.find(user => user.id === userId)
        },
        getOtherUserJourneyById: (state) => (journeyId) => {
            return state.otherUserJourneys.find(journey => journey.id == journeyId)
        },

        getActivitiesByJourneyId: (state) => (journeyId) => {
            return state.usersActivities.filter(activity => activity.journeyId == journeyId)
        },

        // get the journey given the id of the journey
        getJourneyById: (state) => (journeyId) => {
            let otherUserJourney = state.otherUserJourneys.find(journey => journey.journeyId === journeyId)
            let userJourney = state.userJourneys.find(journey => journey.journeyId === journeyId)
            if (otherUserJourney !== undefined) {
                return otherUserJourney;
            } else if (userJourney !== undefined) {
                return userJourney
            } else {
                return undefined
            }
        },

        getFriends(state) {
            return state.friends
        }
    },

    mutations: {
        setUser(state, User) {
            state.user = User
        },

        setSelectedUser(state, selectedUser) {
            state.selectedUser = selectedUser;
        },

        setUserJourneys(state, userJourneys) {
            state.userJourneys = userJourneys
        },

        setJourney(state, journey) {
            state.journey = journey;
        },
        // resets the otherJourneys
        setOtherUserJourneys(state, otherUserJourneys) {
            state.otherUserJourneys = otherUserJourneys
        },

        // resets allOtherJourneys list
        setAllOtherUserJourneys(state, allOtherUserJourneys) {
            state.allOtherUserJourneys = allOtherUserJourneys
        },

        setOtherUsers(state, otherUsers) {
            state.otherUsers = otherUsers
        },

        setJourneyActivities(state, activities) {
            state.journeyActivities = activities
        },

        setLoggedIn(state, value) {
            state.logged_in = value;
        },

        setPlaces(state, places) {
            state.places = places
        },

        setUsersActivities(state, activities) {
            state.usersActivities = activities
        },
        //notification
        addLike(state, like) {
            state.likes.push(like)
        },
        setFriends(state, friends) {
            state.friends = friends
        },
        setFriendsJourney(state, friendsJourney) {
            state.friendsJourney = friendsJourney
        },
        setNotificationActivity(state, notif) {
            state.notificationActivity = notif
        },
        setNotificationJourney(state, journ) {
            state.notificationJourney = journ
        },
        setPhotoArray(state, photoArray) {
            state.photoArray = photoArray;
        },
        setImageId(state, imageId) {
            state.imageId = imageId;
        },
        setActualImage(state, image) {
            state.actualImage = image;
        },
        setImage(state, image) {
            state.image = image;
        }
    },

    actions: {

        // USER //
        async fetchSelectedUser(context, selectedUserId) {
            const res = await fetch(`/users/${selectedUserId}`)
            if (res.status === 403)
                await router.push("/")
            const selectedUser = await res.json()
            return context.commit("setSelectedUser", selectedUser)
        },
        async fetchProfile(context) {
            const res = await fetch("/users/getUser")

            const profile = await res.json()

            return context.commit("setUser", profile)
        },


        async fetchAllOtherUsers(context) {
            const res = await fetch('/users/all')
            const users = await res.json()
            return context.commit("setOtherUsers", users.filter(user => user.id != this.state.user.id))
        },

        async getUsersByNameContaining(context, query) {
            const res = await fetch(`/users/search?partialString=${query}`)
            var users = await res.json()
            var users = users.filter(user => user.id != this.state.user.id)
            return context.commit("setOtherUsers", users)
        },


        // JOURNEYS //

        async fetchJourneyById(context, journeyId) {
            const res = await fetch(`http://localhost:8080/journey/${journeyId}`)
            const journey = await res.json()
            return context.commit("setJourney", journey)
        },

        async fetchAJourneyById(context, journeyId) {
            const res = await fetch(`http://localhost:8080/journey/${journeyId}`)
            return res.json()
        },

        // fetch the journeys of the given user and add it to the list
        async fetchOtherUserJourneys(context, otherUserId) {
            const res = await fetch(`http://localhost:8080/journey/${otherUserId}/journeys`)
            const otherUserJourneys = await res.json()
            return context.commit("setOtherUserJourneys", otherUserJourneys)
        },

        async fetchAllOtherUserJourneys(context) {
            const res = await fetch('http://localhost:8080/journey/all')
            const allOtherUserJourneys = await res.json()
            return context.commit("setAllOtherUserJourneys", allOtherUserJourneys.filter(journey => journey.userId != this.state.user.id))
        },

        async fetchUserJourneys(context, userId) {
            if (this.state.logged_in) {
                const res = await fetch(`/journey/${userId}/journeys`)
                const journeys = await res.json()
                return context.commit("setUserJourneys", journeys)
            } else {
                return context.commit("setUserJourneys", [])
            }
        },

        // This is just a temporary method to fetch all journeys, 'fecthUserJourney' should be used instead
        async fetchJourneys(context) {
            const res = await fetch(`/journey`)
            const userJourneys = await res.json()
            if (userJourneys.length > 0) {
                return context.commit("setOtherUserJourneys", userJourneys)
            }
        },

        async fetchJourneyActivities(context, journeyId) {
            const res = await fetch(`http://localhost:8080/activities/journey/${journeyId}`)
            const activities = await res.json()
            return context.commit("setJourneyActivities", activities)
        },

        async fetchAllActivities(context) {
            const res = await fetch('/activities')
            const activities = await res.json()
            return context.commit("setUsersActivities", activities)
        },

        async changePrivacy(context) {
            const res = await fetch(`/users/${this.state.user.id}/privacy`, {
                method: "PUT",
                headers: {"Content-Type": "application/json"}
            })
            const user = await res.json()
            return context.dispatch("fetchProfile")
        },
        async checkLogin(context) {
            const res = await fetch("/users/is-logged");
            context.commit("setLoggedIn", res.status === 200);
        },

        async getPlacesByNameContaining(context, partialName) {
            const res = await fetch(`http://localhost:8080/places/search?partialName=${partialName}`);
            const places = await res.json()
            console.log("THE PLACE IS: " + JSON.stringify(places))
            return context.commit("setPlaces", places)
        },

        async fetchPlaceById(context, placeId) {
            const res = await fetch(`http://localhost:8080/places/${placeId}`)
            const place = await res.json()
            return context.commit("setPlaces", [place])
        },

        async postStation(context, newStation) {
            const res = await fetch(`http://localhost:8080/places/station`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newStation)
            })
        },

        async postAttraction(context, newAttraction) {
            const res = await fetch(`http://localhost:8080/places/attraction`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newAttraction)
            })
        },

        async postAirport(context, newAirport) {
            const res = await fetch(`http://localhost:8080/places/airport`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newAirport)
            })
        },


        // ACTIVITIES //
        
        async getActivityById(context, id) {
            const res = await fetch(`http://localhost:8080/activities/${id}`, {
                method: "Get"
            })
            return res.json();
        },

        async postTrain(context, obj) {
            var journeyId;
            if (obj.j == undefined) {
                journeyId = obj.jid
            } else {
                const j = obj.j;
                const res = await fetch(`http://localhost:8080/journey/${j.userID}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(j)
                })
                const journey = await res.json();
                journeyId = journey.journeyId;
            }

            const t = obj.trainTrip;
            const edited = t.edited;
            if (edited) {
                await fetch(`http://localhost:8080/journey/trainTrip/${t.from.id}/${t.to.id}/edit`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        id: t.activityID,
                        title: t.title,
                        description: t.description,
                        start: t.start,
                        end: t.end,
                        journeyId: journeyId,
                        type: t.type,
                        coverId: t.coverId
                    })
                })
                return journeyId;
            }
            const res2 = await fetch(`http://localhost:8080/journey/${journeyId}/activities/train`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    id: t.activityID,
                    title: t.title,
                    description: t.description,
                    start: t.start,
                    end: t.end,
                    from: t.from,
                    to: t.to,
                    journeyId: journeyId,
                    type: t.type,
                    coverId: t.coverId
                })
            })
            return journeyId;
        },


        async postVisit(context, obj) {
            var journeyId;
            if (obj.j == undefined) {
                journeyId = obj.jid
            } else {
                const j = obj.j;
                const res = await fetch(`http://localhost:8080/journey/${j.userID}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(j)
                })
                const journey = await res.json();
                journeyId = journey.journeyId;
            }
            const v = obj.visit;
            const edited = v.edited;
            if (edited) {
                await fetch(`http://localhost:8080/journey/visit/${v.location.id}/edit`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        id: v.activityID,
                        title: v.visitTitle,
                        description: v.description,
                        start: v.start,
                        end: v.end,
                        // attraction: v.location,
                        journeyId: journeyId,
                        type: v.type,
                        coverId: v.coverId
                    })
                    })
                    return journeyId
                }
                const res2 = await fetch(`http://localhost:8080/journey/${journeyId}/activities/visit`, {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                    title: v.visitTitle,
                    description: v.description,
                    start: v.start,
                    end: v.end,
                    attraction: v.location,
                    journeyId: journeyId,
                    type: v.type,
                        coverId: v.coverId
                })
            })
            return journeyId;
        },

        async postPlane(context, obj) {
            var journeyId;
            if (obj.j == undefined) {
                journeyId = obj.jid
            } else {
                const j = obj.j;
                const res = await fetch(`http://localhost:8080/journey/${j.userID}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(j)
                })
                const journey = await res.json();
                journeyId = journey.journeyId;
            }

            const t = obj.planeTrip;
            const edited = t.edited;
            if (edited) {

                await fetch(`http://localhost:8080/journey/planeTrip/${t.from.id}/${t.to.id}/edit`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        id: t.activityID,
                        title: t.title,
                        description: t.description,
                        start: t.start,
                        end: t.end,
                        journeyId: journeyId,
                        type: t.type,
                        flightNumber: t.flightNumber,
                        coverId: t.coverId
                    })
                })
                return journeyId
            }
            const res2 = await fetch(`http://localhost:8080/journey/${journeyId}/activities/plane`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    id: t.activityID,
                    title: t.title,
                    description: t.description,
                    start: t.start,
                    end: t.end,
                    from: t.from,
                    to: t.to,
                    journeyId: journeyId,
                    type: t.type,
                    flightNumber: t.flightNumber,
                    coverId: t.coverId
                })
            })
            return journeyId;
        },
        async deleteJourney(context, id) {
            const res = await fetch(`http://localhost:8080/journey/${id}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            })
        },
        async deleteJourneyActivity(context, id) {
            const res = await fetch(`http://localhost:8080/activities/${id}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            })
        },
        async followFriend(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return;
            console.log(obj);
            // const res = await fetch(`http://localhost:8080/friends/${obj.req}/${obj.rec}`, {
            //     method: "POST",
            //     headers: { "Content-Type": "application/json" },
            // })

            const res =  await fetch('http://localhost:8080/notification/friend', {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    to: obj.rec,
                    from: obj.req,
                    notificationType: 'FRIEND_REQUEST',
                    type: ""
                })
            })
            return res.json();

        },
        async existingFriendship(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return;
            console.log(obj)
            const res1 = await fetch(`http://localhost:8080/friends/${obj.req}/${obj.rec}`)
            return res1.status !== 404

        },
        async unfollowFriend(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return;
            console.log(obj);
            const res = await fetch(`http://localhost:8080/friends/${obj.req}/${obj.rec}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" },
            })
        },
        async fetchAllMyFriends(context, user) {
            console.log(user)
            const res = await fetch(`http://localhost:8080/friends/${user}`)
            const friends = await res.json()
            return context.commit("setFriends", friends)
        },
        async fetchAllMyFriendsUnMuted(context, user) {
            console.log(user)
            const res = await fetch(`http://localhost:8080/friends/${user}/unmuted`)
            const friends = await res.json()
            return context.commit("setFriends", friends)
        },
        async fetchAllMyFriendsJourney(context, Id) {
            var J = [];
            for(var i = 0; i < Id.length; i++){
                const res = await fetch(`http://localhost:8080/journey/${Id[i]}/journeys`)
                const friendsJourney = await res.json()
                friendsJourney.forEach(j => {
                    J.push(j);
                })
            }
            return context.commit("setFriendsJourney", J)

        },
        async muteOrUnmuteFriend(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return;
            console.log(obj);
            const res = await fetch(`http://localhost:8080/friends/${obj.req}/${obj.rec}/muteOrUnmute`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
            })
            return res.json();

        },
        async fetchMutedFriends(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return;
            console.log(obj);
            const res = await fetch(`http://localhost:8080/friends/${obj.req}/${obj.rec}/isMuted`)
            return res.json();
        },
        async fetchNotifications(context, id) {
            const res = await fetch(`http://localhost:8080/notification/${id}`, {
                method: "GET",
            })
            const notifications = await res.json()
            return notifications
        },
        async postLikeJourney(context, payload) {
            console.log(JSON.stringify(payload))

            const response = await fetch(`http://localhost:8080/notification/journey`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(payload)
            })
            return response.json()
        },
        async postLikeActivity(context, payload) {
            console.log(JSON.stringify(payload))

            const response = await fetch(`http://localhost:8080/notification/activity`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(payload)
            })
            return response.json()
        },
        async deleteNotification(context, notificationId) {
            const res = await fetch(`http://localhost:8080/notification/${notificationId}`, {
                method: 'DELETE',
                headers: {"Content-Type": "application/json"}
            })
        },
        async hideNotification(context, notificationId) {
            console.log(notificationId)
            const res = await fetch(`http://localhost:8080/notification/hide/${notificationId}`, {
                method: "PUT",
                headers: {"Content-Type": "application/json"}
            })
            // return await res.json();
        },
        async acceptFriendship(context, notification) {
            const res = await fetch('http://localhost:8080/notification/acceptFriendRequest', {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(notification)
            })
        },
        async fetchUserByJourney(context, journeyId) {
            const res = await fetch(`http://localhost:8080/journey/${journeyId}/user`)
            return await res.json();
        },

        async pendingFriendship(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return false;
            const res = await fetch(`http://localhost:8080/notification/isPending/${obj.req}/${obj.rec}`);
            return res.status !== 404
        },
        async pendingFriendshipOne(context, obj) {
            if (obj.req == obj.rec || obj.req == undefined || obj.rec == undefined)
                return false;
            const res = await fetch(`http://localhost:8080/notification/isPending/${obj.req}/${obj.rec}`);
            return await res.json();
        },

        async getTimeZoneFromLocation(context, loc) {
            const res = await fetch(`/api/TimeZone/coordinate?latitude=${loc.latitude}&longitude=${loc.longitude}`, {
                // mode: 'no-cors',
                headers: {"accept": "application/json"}
            })
            const tm = await res.json()
            let offset = tm.currentUtcOffset.seconds / 3600
            if (offset < 0 && Math.abs(offset) < 10) {
                offset = "-0" + Math.abs(offset) + ":00"
            } else if (offset > 0 && Math.abs(offset) < 10) {
                offset = "+0" + Math.abs(offset) + ":00"
            } else if (offset > 0) {
                offset = "+" + offset + ":00"
            } else {
                offset = offset + ":00"
            }

            return {timeZone: tm.timeZone, offset: offset}
        },
        async getNotificationSender(context, notificationId) {
            const res = await fetch(`http://localhost:8080/notification/${notificationId}/getSender`);
            return await res.json();
        },
        async fetchNotificationActivity(context, object) {
            const J = [];
            console.log("FETCHING NOTIFICATION ACTIVITY")
            const res = await fetch(`http://localhost:8080/notification/${object.from}/${object.to}/${object.journey}/${object.activity}/reaction`);
            const notif = await res.json();
            notif.forEach(n => {
                J.push(n);
            })
            return context.commit("setNotificationActivity", J);
        },
        async fetchNotificationJourney(context, object) {
            const J = [];
            console.log("FETCHING NOTIFICATION ACTIVITY")
            const res = await fetch(`http://localhost:8080/notification/${object.from}/${object.to}/${object.journey}/reaction`);
            const notif = await res.json();
            notif.forEach(n => {
                J.push(n);
            })
            return context.commit("setNotificationJourney", J);
        },
        async fetchLikesActivity(context, object) {
            const res = await fetch(`http://localhost:8080/notification/journeyLikes/${object.journey}/${object.activity}`);
            return await res.json();
        },
        async fetchLikesJourney(context, object) {
            const res = await fetch(`http://localhost:8080/notification/journeyLikes/${object.journey}`);
            return await res.json();
        },
        async randomPhoto(context) {
            let J = []
            for(let i= 0; i < 2; i++){
                const res = await fetch(`https://api.unsplash.com/photos/random?client_id=kPbQDEAGwne1T5haZM0riCOzoXLdWrMSk1i6mZSywTE&orientation=portrait`)
                let tmp = await res.json();
                J.push(tmp)
            }
            return context.commit("setPhotoArray", J)
        },
        async choosePhoto(context, query) {
            let J = []
            for(let i= 0; i < 2; i++){
                const res = await fetch(`https://api.unsplash.com/photos/random?client_id=kPbQDEAGwne1T5haZM0riCOzoXLdWrMSk1i6mZSywTE&query=${query}&orientation=portrait`)
                let tmp = await res.json();
                console.log("tmp",tmp)

                for(let j = 0; j < J.length; j++){

                    if(J[j].id == tmp.id){
                        const res1 = await fetch(`https://api.unsplash.com/photos/random?client_id=kPbQDEAGwne1T5haZM0riCOzoXLdWrMSk1i6mZSywTE&query=${query}&orientation=portrait`)
                        tmp = await res1.json();
                    }
                }
                J.push(tmp)
            }

            return context.commit("setPhotoArray", J)
        },
        async postImage(context, image) {
            console.log("ID: " + JSON.stringify(image))
            const res = await fetch(`http://localhost:8080/images/new`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    idImage: image.id,
                    url: image.urls.regular,
                    description: image.description,
                    cropping: "regular",
                    width: image.width,
                    height: image.height,
                })
            })
            return await res.json();
        },
        async getImage(context, imageId) {
            const res = await fetch(`http://localhost:8080/images/${imageId}`)
            let image = await res.json();
            console.log("IMAGEDTO " + JSON.stringify(image))
            return context.commit("setImageId", image)
        },
        async getActualImage(context, actualImageId) {
            const res = await fetch(`http://localhost:8080/images/${actualImageId}/photo`)
            let image = await res.json();
            return context.commit("setActualImage", image)
        },
        async getPhotoById(context, imageId) {
            const res = await fetch(`http://localhost:8080/images/${imageId}/id`)
            let image = await res.json();
            return context.commit("setImage", image)
        }
    }
})
