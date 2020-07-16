$('#rating').likeDislike({
    initialValue: 0,
    click: function (value, l, d, event) {
        var likes = $(this.element).find('.likes');
        var dislikes = $(this.element).find('.dislikes');

        likes.text(parseInt(likes.text()) + l);
        dislikes.text(parseInt(dislikes.text()) + d);

        // $.ajax({
        //     url: 'url',
        //     type: 'post',
        //     data: 'value=' + value,
        // });
    }
});